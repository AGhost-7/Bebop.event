package aghost7.bebop.event
import java.awt.event.{MouseMotionListener, MouseEvent, MouseAdapter}
import java.awt.Component

sealed trait BMouseMotionEvent {
	val ev: MouseEvent
}

case class MouseDragged(ev: MouseEvent) extends BMouseMotionEvent
case class MouseMoved(ev: MouseEvent) extends BMouseMotionEvent

trait MouseMotionListenerExtension {
	val self: Component
	
	def onMouseDragged(func: MouseEvent => Unit) : Unit = 
		self.addMouseMotionListener(new MouseAdapter(){
			override def mouseDragged(ev: MouseEvent): Unit = func(ev)
		})
		
	def onMouseMoved(func: MouseEvent => Unit) : Unit = 
		self.addMouseMotionListener(new MouseAdapter(){
			override def mouseMoved(ev: MouseEvent): Unit = func(ev)
		})
		
	def addBMouseMotionListener(part: PartialFunction[BMouseMotionEvent, Unit]): Unit = {
		val func = part.lift
		self.addMouseMotionListener(new MouseMotionListener(){
			def mouseDragged(ev: MouseEvent): Unit = func(MouseDragged(ev))
			def mouseMoved(ev: MouseEvent): Unit = func(MouseMoved(ev))
		})
	}
}