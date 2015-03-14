package aghost7.bebop.event

import java.awt.event.{ItemEvent, ItemListener}

trait ItemListenerExtension {
		val self: { def addItemListener(itm: ItemListener): Unit }
		
		def onItemStateChanged(func: ItemEvent => Unit): Unit = 
			self.addItemListener(new ItemListener(){
				def itemStateChanged(ev: ItemEvent): Unit = func(ev)
			})
		
		def addBItemListener(callback: ItemEvent => Unit) : Unit = 
			self.addItemListener(new ItemListener(){
				def itemStateChanged(ev: ItemEvent) : Unit = callback(ev)
			})
	}