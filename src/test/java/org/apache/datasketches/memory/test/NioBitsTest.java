/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.datasketches.memory.test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Lee Rhodes
 */
@SuppressWarnings("javadoc")
public class NioBitsTest {

  @Test
  public void checkVMParams() {
    println("Max MemoryImpl: " + ReflectUtil.getMaxDirectByteBufferMemory());
    println("Page Aligned: " + ReflectUtil.isPageAligned());
    println("Page Size: " + ReflectUtil.pageSize());
  }
  
  @Test
  //testing this beyond 2GB may not work on JVMs < 8GB.
  //This must be checked manually
  public void checkGetAtomicFields() {
    long cap = 1024L + Integer.MAX_VALUE;
    printStats();
    ReflectUtil.reserveMemory(cap, cap);
    printStats();
    ReflectUtil. unreserveMemory(cap, cap);
    printStats();
  }

  @Test
  public void checkPageCount() {
    assertEquals(ReflectUtil.pageCount(0), 0);
    assertEquals(ReflectUtil.pageCount(1), 1);
  }

  private static void printStats() {
    long count = ReflectUtil.getDirectAllocationsCount();
    long resMem = ReflectUtil.getReservedMemory();
    long totCap = ReflectUtil.getTotalCapacity();
    long maxDBBmem = ReflectUtil.getMaxDirectByteBufferMemory();
    String s = String.format("%,10d\t%,15d\t%,15d\t%,15d", count, resMem, totCap, maxDBBmem);
    println(s);
  }

  @Test
  public void printlnTest() {
    println("PRINTING: " + this.getClass().getName());
  }

  /**
   * @param s value to print
   */
  static void println(final String s) {
    //System.out.println(s); //disable here
  }

}
