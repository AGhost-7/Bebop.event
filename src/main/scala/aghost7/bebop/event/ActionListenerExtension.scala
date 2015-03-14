package aghost7.bebop.event

import java.awt.event.{ActionListener, ActionEvent}

private [event] class CallbackActionListener(func: ActionEvent => Unit) 
		extends ActionListener {
	def actionPerformed(ev: ActionEvent): Unit = func(ev)
}

trait ActionListenerExtension {
		val self: { def addActionListener(act: ActionListener): Unit }
		
		def onActionPerformed(func: ActionEvent => Unit): ActionListener = {
			val l = new CallbackActionListener(func)
			self.addActionListener(l)
			l
		}
		
		def addBActionListener(func: ActionEvent => Unit) : ActionListener = 
			onActionPerformed(func)
			
}