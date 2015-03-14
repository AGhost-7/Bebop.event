package aghost7.bebop.event

import java.awt.event.{MouseWheelListener, MouseWheelEvent, MouseAdapter}
import java.awt.Component

trait MouseWheelListenerExtension {
	val self: Component
	
	def onMouseWheelMoved(func: MouseWheelEvent => Unit): MouseWheelListener = {
		val l = new MouseWheelListener(){
			def mouseWheelMoved(ev: MouseWheelEvent): Unit = func(ev)
		}
		self.addMouseWheelListener(l)
		l
	}
		
		
	def addBMouseWheelListener(func: MouseWheelEvent => Unit): MouseWheelListener = {
		val l = new MouseWheelListener(){
			def mouseWheelMoved(ev: MouseWheelEvent): Unit = func(ev)
		}
		self.addMouseWheelListener(l)
		l
	}
		
	
}