Employee = load 'employee' using PigStorage(' ') as (fname, mname, lname, ssn, date, year, dloc, mfname, mmname, mlname, salary, mgrssn, age);
Filtered = filter Employee by age >= 4 and age <= 6;
Works_On = load 'works_on' using PigStorage(' ') as (ussn, dno,hours);
Joined = join Filtered by ssn, Works_On by ussn;
store Works_On into 'muku';
