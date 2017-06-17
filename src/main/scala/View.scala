import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import State.effects._
import korolev._

/**
  * @author Aleksey Fomkin <aleksey.fomkin@gmail.com>
  */
class View {

  // Handler to input
  val inputId = elementId

  // Generate actions when clicking checkboxes
  val todoClick: EventFactory[(Int, Todo)] = { case (i, todo) =>
    event('click, EventPhase.AtTarget) {
      immediateTransition { case state =>
        val updated = state.todos.updated(i, state.todos(i).copy(done = !todo.done))
        state.copy(todos = updated)
      }
    }
  }

  // Generate AddTodo action when 'Add' button clicked
  val addTodoClick: Event =
    eventWithAccess('click) { access =>
      deferredTransition {
        access.property[String](inputId, 'value) map { value =>
          val todo = Todo(value, done = false)
          transition {
            case state =>
              state.copy(todos = state.todos :+ todo)
          }
        }
      }
    }

  // Create a DOM using state
  def render: Render[State] = {
    case state =>
      'body(
        'div("Super TODO tracker"),
        'div('style /= "height: 250px; overflow-y: scroll",
          (state.todos zipWithIndex) map {
            case (todo, i) =>
              'div(
                'input(
                  'type /= "checkbox",
                  'checked when todo.done,
                  todoClick(i, todo)
                ),
                if (!todo.done) 'span(todo.text)
                else 'strike(todo.text)
              )
          }
        ),
        'div(
          'input(
            inputId,
            'type /= "text",
            'placeholder /= "What should be done?"
          ),
          'button(
            addTodoClick,
            "Submit"
          )
        )
      )
  }

}
