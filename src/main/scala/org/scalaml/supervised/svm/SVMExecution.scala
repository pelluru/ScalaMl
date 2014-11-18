/**
 * Copyright 2013, 2014  by Patrick Nicolas - Scala for Machine Learning - All rights reserved
 *
 * The source code in this file is provided by the author for the sole purpose of illustrating the 
 * concepts and algorithms presented in "Scala for Machine Learning" ISBN: 978-1-783355-874-2 Packt Publishing.
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * Version 0.95e
 */
package org.scalaml.supervised.svm

import libsvm._



		/**
		 * <p>Protected class that encapsulates the execution parameters for SVM training. The
		 * class are instantiated by the companion object.<br><br>
		 * <b>cacheSize</b> Size of the cache used in LIBSVM to preserve intermediate computation during training.<br>
		 * <b>eps</b> Convergence Criteria to exit the training cycle<b>
		 * <b>nFolds</b> Number of folds used in K-fold validation of the SVM model.</p>
		 * @constructor Create SVM execution configuration with the following parameters
		 * @throws IllegalArgumentException if the cache, convergence criteria or number of folds are incorrectly specified.
		 * 		 
		 * @author Patrick Nicolas
		 * @since April 28, 2014
		 * @note Scala for Machine Learning Chapter 8 Kernel models and support vector machines.
 		 */
protected class SVMExecution(cacheSize: Int, val eps: Double, val nFolds: Int) extends SVMConfigItem {
	import SVMExecution._
	
	check(cacheSize, eps, nFolds)
	override def update(param: svm_parameter): Unit = {
		param.cache_size = cacheSize
		param.eps = eps
	}

	override def toString: String = new StringBuilder("\nCache size: ").append(cacheSize).append("\neps: ").append(eps).toString
}



object SVMExecution {
	final val DEFAULT_CACHE_SIZE=2000

	def apply(cacheSize: Int, eps: Double, nFolds: Int): SVMExecution = 
		new SVMExecution(cacheSize, eps,nFolds)

	def apply(eps: Double, nFolds: Int): SVMExecution = 
		new SVMExecution(DEFAULT_CACHE_SIZE, eps, nFolds)
	
	def apply(eps: Double): SVMExecution = 
		new SVMExecution(DEFAULT_CACHE_SIZE, eps, -1)
	
	final val MAX_CACHE_SIZE = 1<<8
	final val EPS_LIMITS = (1e-10, 0.35)
	private def check(cacheSize: Int, eps: Double, nFolds: Int): Unit = {
		require(cacheSize >= 0 && cacheSize < MAX_CACHE_SIZE, s"SVMExecution.check $cacheSize is out of range")
		require(eps > EPS_LIMITS._1 && eps < EPS_LIMITS._2, s"SVMExecution.check  $eps is out of range")
		require(nFolds > 1 && nFolds < 10, s"SVMExecution.check THe number of folds for validtion $nFolds is out of range")
	}
}


// --------------------------- EOF ------------------------------------------