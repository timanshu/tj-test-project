package  conf.evolutions.default
# --- !Ups
CREATE TABLE  students( 
    roll int,
    name varchar, 
    stream varchar
	);

# --- !Downs
DROP TABLE IF EXISTS students;
