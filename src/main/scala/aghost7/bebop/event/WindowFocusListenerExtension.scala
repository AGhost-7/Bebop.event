package aghost7.bebop.event

import java.awt.event.{WindowFocusListener, WindowEvent, WindowAdapter}
import java.awt.Window


sealed trait BWindowFocusEvent {
	val ev: WindowEvent
}

case class WindowGainedFocus(ev: WindowEvent) extends BWindowFocusEvent
case class WindowLostFocus(ev: WindowEvent) extends BWindowFocusEvent

trait WindowFocusListenerExtension {
	val self: Window
	
	def onWindowGainedFocus(func: WindowEvent => Unit): WindowFocusListener ={
		val l = new WindowAdapter(){
			override def windowGainedFocus(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
	
	def onWindowLostFocus(func: WindowEvent => Unit): WindowFocusListener = {
		val l = new WindowAdapter(){
			override def windowLostFocus(ev: WindowEvent): Unit = func(ev)
		}
		self.addWindowFocusListener(l)
		l
	}
		
		
	def addBWindowFocusListener(part: PartialFunction[BWindowFocusEvent, Unit]): WindowFocusListener = {
		val func = part.lift
		val l = new WindowFocusListener(){
			def windowGainedFocus(ev: WindowEvent): Unit =
				func(WindowGainedFocus(ev))
			def windowLostFocus(ev: WindowEvent): Unit = 
				func(WindowLostFocus(ev))
		}
		self.addWindowFocusListener(l)
		l
	}
}