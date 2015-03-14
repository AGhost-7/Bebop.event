package aghost7.bebop.event

import java.awt.event.{ComponentEvent, ComponentListener, ComponentAdapter}
import java.awt.Component

sealed trait BComponentEvent {
	val ev: ComponentEvent
}

case class ComponentHidden(ev: ComponentEvent) extends BComponentEvent
case class ComponentMoved(ev: ComponentEvent) extends BComponentEvent
case class ComponentResized(ev: ComponentEvent) extends BComponentEvent
case class ComponentShown(ev: ComponentEvent) extends BComponentEvent

trait ComponentListenerExtension {
	val self: Component
	
	def onComponentHidden(func: ComponentEvent => Unit) : Unit = {
		self.addComponentListener(new ComponentAdapter(){
			override def componentHidden(ev: ComponentEvent): Unit = func(ev)
		})
	}
	
	def onComponentMoved(func: ComponentEvent => Unit): Unit = {
		self.addComponentListener(new ComponentAdapter(){
			override def componentMoved(ev: ComponentEvent): Unit = func(ev)
		})
	}
	
	def onComponentResized(func: ComponentEvent => Unit): Unit = {
		self.addComponentListener(new ComponentAdapter(){
			override def componentResized(ev: ComponentEvent): Unit = func(ev)
		})
	}
	
	def onComponentShown(func: ComponentEvent => Unit): Unit = {
		self.addComponentListener(new ComponentAdapter(){
			override def componentShown(ev: ComponentEvent): Unit = func(ev)
		})
	}
	
	def addBComponentListener(part: PartialFunction[BComponentEvent, Unit]): Unit = {
		val func = part.lift
		self.addComponentListener(new ComponentListener(){
			def componentHidden(ev: ComponentEvent): Unit = 
				func(ComponentHidden(ev))
			def componentMoved(ev: ComponentEvent): Unit =
				func(ComponentResized(ev))
			def componentResized(ev: ComponentEvent): Unit = 
				func(ComponentResized(ev))
			def componentShown(ev: ComponentEvent): Unit =
				func(ComponentShown(ev))
		})
	}
}