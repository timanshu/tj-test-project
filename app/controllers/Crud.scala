package controllers

import play.api._
import play.api.mvc._
import models.Student

class Crud extends Controller{
    def write = Action(parse.json) { request => //accept only json data
        val jsvalue = request.body  //parse the request
        val inStudent = jsvalue.as[Student]
        if (Student.insert(inStudent))
            Created("<h1>Student succesufully created</h1>")
        else
            Conflict("<h1>Error</h1>")
    }
    
    def edit = Action(parse.json) { request => //accept only json data
        val jsvalue = request.body  //parse the request
        val inStudent = jsvalue.as[Student]
        if (Student.update(inStudent))
            Created("<h1>Student succesufully updated</h1>")
        else
            Conflict("<h1>Error</h1>")
    }
    
    def delete(roll:Long) = Action { request =>
        if(!Student.delete(roll))
            Ok("<h1>Student succesufully deleted</h1>")
        else
            Conflict("<h1>Error</h1>")
    }
    
    def read(roll:Long) = Action { request =>
        val stu : List[Student] = Student.get(roll)
        Ok(views.html.student(stu))
    }
}