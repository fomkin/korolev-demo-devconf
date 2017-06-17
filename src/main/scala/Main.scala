import korolev.blazeServer.{KorolevBlazeServer, blazeService}
import korolev.server.StateStorage.{DeviceId, SessionId}
import korolev.server.{KorolevServiceConfig, ServerRouter, StateStorage}
import korolev.execution._
import scala.concurrent.Future


/**
  * @author Aleksey Fomkin <aleksey.fomkin@gmail.com>
  */
object Main extends KorolevBlazeServer {

  val TodosFile = "todos.txt"

  val service = blazeService[Future, State, Any] from KorolevServiceConfig [Future, State, Any](
    serverRouter = ServerRouter.empty[Future, State],
    stateStorage = new StateStorage[Future, State] {

      def initial(deviceId: DeviceId): Future[State] = {
        val state = State.readFromFile(TodosFile)
        Future.successful(state)
      }

      def read(deviceId: DeviceId, sessionId: SessionId): Future[State] = {
        val state = State.readFromFile(TodosFile)
        Future.successful(state)
      }

      def write(deviceId: DeviceId, sessionId: SessionId, value: State): Future[State] = {
        value.writeToFile(TodosFile)
        Future.successful(value)
      }
    },
    render = {
      val view = new View()
      view.render
    }
  )

}
