package com.arinaldoferreira.cursojava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Teste {

	public static void main(String[] args) {
	
		final Logger LOG = LoggerFactory.getLogger(CursojavaApplication.class);
		
		int j = 3;
		int k = 4;
		double l = 5.5;
		

		long tempoInicial = System.currentTimeMillis();
		for (int i=0;i<100;i++) {
			LOG.info("i" + i + ", j" + j + ", k" + k + ", l" + l);

		}
		System.out.println("o metodo CONCATENADO executou em " + (System.currentTimeMillis() - tempoInicial));
		
		tempoInicial = System.currentTimeMillis();
		for (int i=0;i<100;i++) {		
			LOG.info("i{} j{} k{} l{}",i,j,k,l);
		}
		System.out.println("o metodo {} executou em " + (System.currentTimeMillis() - tempoInicial));
	}
}