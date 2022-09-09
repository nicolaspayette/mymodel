import org.nlogo.headless.HeadlessWorkspace
import org.scalatest.funsuite.AnyFunSuite

class SetSuite extends AnyFunSuite {

  test("Run model tests") {
      val workspace = HeadlessWorkspace.newInstance
      workspace.open("my-model.nlogo")
      try {
        workspace.command("test")
      } catch {
        case ex: Exception =>
          // NetLogo exceptions are not serializable and ScalaTest complains about
          // that so we re-wrap the exception message in a new generic exception.
          val msg = ex.getMessage + "\n" +  ex.getStackTrace.map("  " + _).mkString("\n")
          throw new Exception(msg)
      } finally {
        workspace.dispose()
      }
  }
}
