package models

import play.api.db._
import play.api.Play.current
 
import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Student(
    roll: Long,
    name: String, 
    stream: String
)

object Student{

def get(rollno:Long): List[Student] = DB.withConnection {
                    implicit connection =>
                    import anorm.Row
                    val sql: SqlQuery = SQL("select * from students where roll = "+rollno)
                    sql().map ( row =>
                        Student(row[Long]("roll"), row[String]("name"),row[String]("stream"))
                    ).toList
}

def insert(student: Student): Boolean = 
	DB.withConnection { implicit connection => 
	val addedRows = SQL("insert into students values ({roll}, {name} , {stream})").on(
	            "roll" -> student.roll, 
	            "stream" -> student.stream, 
	            "name" -> student.name 
	            ). executeUpdate() 
	addedRows == 1
}


def update(student: Student): Boolean = 
DB.withConnection { implicit connection => 
val updatedRows = SQL("""update students 
            set name = {name},
			stream = {stream} 
			where roll = {roll} """
			).on(
			"roll" -> student.roll,
			"name" -> student.name, 
			"stream" -> student.stream
			). executeUpdate() 
	updatedRows == 1 
	}
	
def delete(roll:Long): Boolean = 
	DB.withConnection { implicit connection => 
	val updatedRows = SQL("delete from students where roll = {roll}"
	). on("roll" -> roll).executeUpdate() 
	updatedRows == 0
 } 
 
implicit val studentReads: Reads[Student] = (
            (JsPath \\ "roll").read[Long] and
            (JsPath \\ "name").read[String] and 
            (JsPath \\ "stream").read[String]
            )(Student.apply _)

}