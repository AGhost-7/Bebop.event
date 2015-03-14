package aghost7.bebop.event

import javax.swing.SwingUtilities
import java.util.concurrent.Executors
import java.awt.event.ActionEvent
import javax.swing.{Timer => Looper}

private [event] class CallbackRunnable(callback: => Unit) extends Runnable {
	def run: Unit = callback
}

/** Not quite round-robin, but definitively similar. */
private [event] class RoundRobinScheduler extends Runnable {
	
	@volatile var quant = 0L
	@volatile var active = true
	var scheduled: List[(Long, () => Unit)] = Nil
	
	val unit = 50L
	
	val executor = Executors.newSingleThreadExecutor()
	val thread = new Thread(this)
	
	thread.start
	
	def run: Unit = {
		while(active){
			Thread.sleep(unit)
			quant += 1L
			scheduled.synchronized {
				if(!scheduled.isEmpty){
					val (startAt, callback) = scheduled(0)
					if(quant >= startAt){
						scheduled = scheduled.tail
						executor.execute(new Runnable(){
							def run = callback()
						})
					}
				}
			}
			
		}
	}
	
	def schedule(millis: Long, callback: () => Unit): Unit = {
		val startAt = quant + (millis / unit)
		val group: (Long, () => Unit) = (startAt, callback)
		scheduled.synchronized{
			scheduled = scheduled :+ group
		}
	}
}

object BConcurrent {
	@volatile private var schedulerActive = false
	private lazy val scheduler = {
		schedulerActive = true
		new RoundRobinScheduler
	}
	
	def invokeAndWait(callback: => Unit): Unit = {
		SwingUtilities.invokeAndWait(new CallbackRunnable(callback))
	}
	
	def invokeLater(callback: => Unit): Unit = {
		SwingUtilities.invokeLater(new CallbackRunnable(callback))
	}
	
	def invokeLater(delay: Long)(callback: => Unit): Unit = {
		delayCommand(delay) {
			invokeLater(callback)
		}
	}
	
	/** DelayCommand will fire the callback after a number of milliseconds 
	 *  specified. Note that your function will be called from another thread. 
	 *  
	 *  This version will spawn a new thread each time it is invoked.
	 */
	def delayCommandOnce(millis: Long)(callback: => Unit): Unit = {
		new Thread {
			override def run: Unit = {
				Thread.sleep(millis)
				callback
			}
		}.start()
	}
	
	/** A scheduler which is lazily initialized will be used to schedule the 
	 *  event in the future. Note that this spawns 2 threads, a scheduling thread,
	 *  and a executor thread. Also, this has 50 milliseconds precision, and any
	 *  callback done using this shouldn't take any longer than 50 milliseconds
	 *  to execute.
	 */
	def delayCommand(millis: Long)(callback: => Unit): Unit = {
		// can't get the syntax right otherwise...
		scheduler.schedule(millis, () => callback)
	}
	
	
	/** Starts a javax.swing.Timer instance and returns it. */
	def looping(millis: Int)(callback: ActionEvent => Unit) : Looper = {
		val l = new Looper(millis, new CallbackActionListener(callback))
		l.start
		l
	}
	
	/** Gracefully shuts down the internal scheduler if it was initialized. */
	def closeScheduler: Unit = {
		// don't want to initialize just to close it down
		if(schedulerActive){
			scheduler.active = false
			scheduler.executor.shutdown()
		}
	}
}

