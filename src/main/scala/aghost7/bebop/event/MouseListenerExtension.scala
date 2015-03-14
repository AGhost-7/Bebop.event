package aghost7.bebop.event

import java.awt.Component
import java.awt.event.{MouseEvent, MouseListener, MouseAdapter}

sealed trait BMouseEvent

case class MouseClicked(ev: MouseEvent) extends BMouseEvent
case class MouseEntered(ev: MouseEvent) extends BMouseEvent
case class MouseExited(ev: MouseEvent) extends BMouseEvent
case class MousePressed(ev: MouseEvent) extends BMouseEvent
case class MouseReleased(ev: MouseEvent) extends BMouseEvent

trait MouseListenerExtension {
	val self: Component
	
	def onMouseClicked(func: MouseEvent => Unit): Unit = 
		self.addMouseListener(new MouseAdapter(){
			override def mouseClicked(ev: MouseEvent): Unit = func(ev)
		})
	
	def onMouseEntered(func: MouseEvent => Unit): Unit = 
		self.addMouseListener(new MouseAdapter(){
			override def mouseEntered(ev: MouseEvent): Unit = func(ev)
		})
		
	def onMouseExited(func: MouseEvent => Unit): Unit = 
		self.addMouseListener(new MouseAdapter(){
			override def mouseExited(ev: MouseEvent): Unit = func(ev)
		})
		
	def onMousePressed(func: MouseEvent => Unit): Unit = 
		self.addMouseListener(new MouseAdapter(){
			override def mousePressed(ev: MouseEvent): Unit = func(ev)
		})
		
	def onMouseReleased(func: MouseEvent => Unit): Unit = 
		self.addMouseListener(new MouseAdapter(){
			override def mouseReleased(ev: MouseEvent): Unit = func(ev)
		})
		
	def addBMouseListener(part: PartialFunction[BMouseEvent, Unit]): Unit = {
		val func = part.lift
		self.addMouseListener(new MouseListener(){
			def mouseClicked(ev: MouseEvent): Unit = func(MouseClicked(ev))
			def mouseEntered(ev: MouseEvent): Unit = func(MouseEntered(ev))
			def mouseExited(ev: MouseEvent): Unit = func(MouseExited(ev))
			def mousePressed(ev: MouseEvent): Unit = func(MousePressed(ev))
			def mouseReleased(ev: MouseEvent): Unit = func(MouseReleased(ev))
		})
	}
}