package policyreader;

/**
 * This class validates the user request against the provided policy for the resource to permit/Deny access to the requester.
 * @author Manoj,Mukunthan,Meenakshi Bala,Harish Babu
 *
 */
public class XACMLPolicyValidator {

	/**
	 * This method evaluates user request for legitimate access against the resources.
	 * @param resources This string array holds all the resources for which request is generated
	 * @param user This is the user who access the resources  
	 * @return accessPermit (true - 1, false -0)
	 */
	public static boolean validateLegitimateAccess(String[] resources,String user)throws Exception{
		boolean accessPermit = false;
		String[] policyFiles = new String[1];
		XPDP simplePDP ;
		boolean[] permit;
		for(String resource:resources){
			policyFiles[0]= "/home/mukunthan/workspace_new/SecuredCloud/"+resource+"Policy.xml";
			simplePDP = new XPDP(policyFiles);
			permit = new boolean[1];
			simplePDP.run("/home/mukunthan/workspace_new/SecuredCloud/xml/groups.xml", resource, simplePDP, user, permit);
			if(permit[0]==false){
				accessPermit = false;
				break;
			}else{
				accessPermit = true;
			}
		}
		
		return accessPermit;
	}
}
