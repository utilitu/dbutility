package com.optum.wpi.batch.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApp {

	public static void main(String[] args) {
		CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
		System.out.println(cf.isDone());
		assertTrue(cf.isDone());
		assertEquals("message", cf.getNow(null));
	}
	
	@Test
	public void dummyTest() {
		System.out.println("dummyTest");
	}
}
