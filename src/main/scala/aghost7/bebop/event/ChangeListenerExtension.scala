package aghost7.bebop.event
import javax.swing.event.{ChangeListener, ChangeEvent}

trait ChangeListenerExtension {
	val self: { def addChangeListener(listen: ChangeListener): Unit }
	
	def onStateChanged(func: ChangeEvent => Unit): ChangeListener = {
		val l = new ChangeListener{
			def stateChanged(ev: ChangeEvent): Unit = func(ev)
		}
		self.addChangeListener(l)
		l
	}
	
	def addChangeListener(func: ChangeEvent => Unit): ChangeListener = 
		onStateChanged(func)
}