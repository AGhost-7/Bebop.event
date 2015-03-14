package aghost7.bebop.event

import java.awt.event.{WindowFocusListener, WindowEvent, WindowAdapter}
import java.awt.Window


sealed trait BWindowFocusEvent

case class WindowGainedFocus(ev: WindowEvent) extends BWindowFocusEvent
case class WindowLostFocus(ev: WindowEvent) extends BWindowFocusEvent

trait WindowFocusListenerExtension {
	val self: Window
	
	def onWindowGainedFocus(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowGainedFocus(ev: WindowEvent): Unit = func(ev)
		})
	
	def onWindowLostFocus(func: WindowEvent => Unit): Unit =
		self.addWindowFocusListener(new WindowAdapter(){
			override def windowLostFocus(ev: WindowEvent): Unit = func(ev)
		})
		
	def addBWindowFocusListener(part: PartialFunction[BWindowFocusEvent, Unit]): Unit = {
		val func = part.lift
		self.addWindowFocusListener(new WindowFocusListener(){
			def windowGainedFocus(ev: WindowEvent): Unit =
				func(WindowGainedFocus(ev))
			def windowLostFocus(ev: WindowEvent): Unit = 
				func(WindowLostFocus(ev))
		})
	}
}