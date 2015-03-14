package aghost7.bebop.event

import java.awt.event.{MouseWheelListener, MouseWheelEvent, MouseAdapter}
import java.awt.Component

trait MouseWheelListenerExtension {
	val self: Component
	
	def onMouseWheelMoved(func: MouseWheelEvent => Unit): Unit = 
		self.addMouseWheelListener(new MouseWheelListener(){
			def mouseWheelMoved(ev: MouseWheelEvent): Unit = func(ev)
		})
		
	def addBMouseWheelListener(func: MouseWheelEvent => Unit): Unit = 
		self.addMouseWheelListener(new MouseWheelListener(){
			def mouseWheelMoved(ev: MouseWheelEvent): Unit = func(ev)
		})
	
}