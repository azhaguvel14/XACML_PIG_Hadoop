Users = load 'users' using PigStorage(':') as (name, age);
Filtered = filter Users by age >= 18 and age <= 25;
Pages = load 'pages' using PigStorage(':') as (user, url);
Joined = join Filtered by name, Pages by user;
Grouped = group Joined by url;
Summed = foreach Grouped generate group, COUNT(Joined) as clicks;
Sorted = order Summed by clicks desc;
Top5 = limit Sorted 5;
store Top5 into 'top5sites';
