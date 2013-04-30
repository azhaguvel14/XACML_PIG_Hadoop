package password;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.pig.ExecType;
import org.apache.pig.PigServer;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.backend.hadoop.datastorage.ConfigurationUtil;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 * This class executes the pig script given by the user.Also the user passwords are encrypted using basic password encryptor.
 * @author mukunthan,manoj,meenakshi,harish
 *
 */

public class PasswordEncryption {

	/**
	 * This method parses pig script into individual queries and executes over hadoop file data.
	 * @param pigScript User submitted pig script 
	 * @return Str A String Buffer with the results
	 */	
	public static StringBuffer callPigScript(String pigScript){
		PigServer pigServer;
		String path = "";
		String[] queryArray=pigScript.split(";");
		StringBuffer str = new StringBuffer();
		try {
			
			Properties propes = System.getProperties();
			propes.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
			
			Properties props = new Properties();
			props.setProperty("fs.default.name", "hdfs://localhost:54310");
			props.setProperty("mapred.job.tracker", "hdfs://localhost:54311");
			pigServer = new PigServer(ExecType.MAPREDUCE, props);
			for(String s: queryArray){
				pigServer.registerQuery(s+";");
			}
			
			FileSystem fs = FileSystem.get(ConfigurationUtil.toConfiguration(pigServer.getPigContext().getProperties()));
			if(fs.exists(new Path("muku" + "/part-m-00000")))
				path=("muku" + "/part-m-00000");
			else if(fs.exists(new Path("muku" + "/part-r-00000")))
				path=("muku" + "/part-r-00000");
			if(!path.equals("")) {				
				BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(new Path(path))));
				fs.deleteOnExit(new Path("muku"));
				try {
					String line;
				    line=br.readLine();
				    while (line != null){
				    	str.append(line);
				    	str.append("<BR/>");
				        line = br.readLine();
				      }
				}	
				catch(Exception e)
				{
					e.printStackTrace();
				}
				br.close();
			    fs.close();		

				}
			} catch (ExecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return str;
	}
	
	/**
	 * This method validates the user against the encrypted password which is placed in hadoop file system
	 * @param username User name from session attribute.
	 * @param password Plain password provided by the user.
	 * @return Returns if valid user or not
	 * @throws FileNotFoundException
	 */
	public static String ValidateUser(String username, String password) throws FileNotFoundException{
		String[] usernames = new String[100]; 
		String[] passwords = new String[100];
		String msg = null;
		PigServer pigServer;
		try
		{
			Properties props = new Properties();
			Properties propes = System.getProperties();
			propes.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
			props.setProperty("fs.default.name", "hdfs://localhost:54310");
			props.setProperty("mapred.job.tracker", "hdfs://localhost:54311");
			pigServer = new PigServer(ExecType.MAPREDUCE, props);
			FileSystem fs = FileSystem.get(ConfigurationUtil.toConfiguration(
					pigServer.getPigContext().getProperties()));
			
			//Following code retrieves the user and password to authenticate
			BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(new Path("secure" + "/grouptomember.txt"))));
			try {
				String line;
			    line=br.readLine();
			    int i=0;
			    while (line != null){
					String[] info = line.split(",");
					usernames[i]= info[1];
					passwords[i] = info[2];
			        line = br.readLine();
			        i++;
			      }
			}	
			catch(Exception e)
			{
				e.printStackTrace();
			}
			br.close();
		    fs.close();	
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			for(int i=0;i<usernames.length;i++){
				if(usernames[i].equals(username)){
					if (passwordEncryptor.checkPassword(password, passwords[i])) {
						  msg="Success";
						} else {
							msg="Incorrect Password";
						}
					break;
				}
				else{
					msg="Incorrect Username";
				}
			}
		} catch(Exception e)	{
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * This main method generates encrypted passwords to be stored in the password file placed in hadoop.
	 * @param args
	 * Generates the needed encryption passwords
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
			//Passwords to be encrypted and placed safely in hadoop file system.
			String[] passwords={"admin","guest","manoj","babu","bala"};
			String[] encryptedPasswords=new String[passwords.length];
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			for(int i=0;i<passwords.length;i++){
				encryptedPasswords[i]=passwordEncryptor.encryptPassword(passwords[i]);
			}
			String newLine = System.getProperty("line.separator");
			FileWriter writer;
	        try {
	        	/**
	        	 * Writes the temporary password file to passwords.txt
	        	 */
	          writer = new FileWriter("/home/mukunthan/passwords.txt");
	          for(int i=0;i<encryptedPasswords.length;i++){
	            writer.write(encryptedPasswords[i]+newLine);
	          }
	          writer.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
			
	}
	

}
