package aghost7.bebop.event

import java.awt.event.{ItemEvent, ItemListener}

trait ItemListenerExtension {
		val self: { def addItemListener(itm: ItemListener): Unit }
		
		def onItemStateChanged(func: ItemEvent => Unit): ItemListener = {
			val l = new ItemListener(){
				def itemStateChanged(ev: ItemEvent) : Unit = func(ev)
			}
			self.addItemListener(l)
			l
		}
		
		def addBItemListener(func: ItemEvent => Unit) : ItemListener = 
			onItemStateChanged(func)
			
	}