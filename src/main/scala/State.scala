import java.io.PrintWriter

import korolev.execution._
import scala.concurrent.Future
import scala.io.Source

object State {

  val effects = korolev.Effects[Future, State, Any]

  def readFromFile(fileName: String): State = State(
    todos = Source.fromFile(fileName).getLines.toVector map { line =>
      line.split(" ", 2) match {
        case Array("[*]", text) => Todo(text, done = true)
        case Array(_, text) => Todo(text, done = false)
      }
    }
  )
}

case class State(todos: Vector[Todo]) {

  def writeToFile(fileName: String): Unit = new PrintWriter(fileName) {
    val lines = todos map {
      case Todo(text, true) => s"[*] $text\n"
      case Todo(text, false) => s"[_] $text\n"
    }
    // Write lines to file and close
    lines.foreach(write)
    close()
  }
}
