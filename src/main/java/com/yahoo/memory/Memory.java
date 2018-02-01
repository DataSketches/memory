/*
 * Copyright 2017, Yahoo! Inc. Licensed under the terms of the
 * Apache License 2.0. See LICENSE file at the project root for terms.
 */

package com.yahoo.memory;

import static com.yahoo.memory.UnsafeUtil.LS;
import static com.yahoo.memory.UnsafeUtil.unsafe;
import static com.yahoo.memory.Util.nullCheck;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Provides read-only primitive and primitive array methods to any of the four resources
 * mentioned in the package level documentation.
 *
 * @author Roman Leventov
 * @author Lee Rhodes
 *
 * @see com.yahoo.memory
 */
public abstract class Memory {

  //BYTE BUFFER XXX
  /**
   * Accesses the given ByteBuffer for read-only operations.
   * @param byteBuf the given ByteBuffer, must not be null
   * @return the given ByteBuffer for read-only operations.
   */
  public static Memory wrap(final ByteBuffer byteBuf) {
    if ((byteBuf != null) && (byteBuf.capacity() == 0)) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    final ResourceState state = new ResourceState();
    state.putByteBuffer(byteBuf);
    AccessByteBuffer.wrap(state);
    final WritableMemoryImpl impl = new WritableMemoryImpl(state);
    return impl;
  }

  //MAP XXX
  /**
   * Allocates direct memory used to memory map entire files for read operations
   * (including those &gt; 2GB). This assumes that the file was written using native byte ordering.
   * @param file the given file to map
   * @return MemoryMapHandler for managing this map
   * @throws Exception file not found or RuntimeException, etc.
   */
  public static MapHandle map(final File file) throws Exception {
    return map(file, 0, file.length(), ByteOrder.nativeOrder());
  }

  /**
   * Allocates direct memory used to memory map files for read operations
   * (including those &gt; 2GB).
   * @param file the given file to map
   * @param fileOffset the position in the given file
   * @param capacity the size of the allocated direct memory
   * @param byteOrder the endianness of the given file.
   * @return MemoryMapHandler for managing this map
   * @throws Exception file not found or RuntimeException, etc.
   */
  public static MapHandle map(final File file, final long fileOffset, final long capacity,
      final ByteOrder byteOrder) throws Exception {
    final ResourceState state = new ResourceState();
    state.putFile(file);
    state.putFileOffset(fileOffset);
    state.putCapacity(capacity);
    state.order(byteOrder);
    return MapHandle.map(state);
  }

  //REGIONS XXX
  /**
   * Returns a read only region of this Memory.
   * @param offsetBytes the starting offset with respect to this Memory
   * @param capacityBytes the capacity of the region in bytes
   * @return a read only region of this Memory
   */
  public abstract Memory region(long offsetBytes, long capacityBytes);

  //BUFFER XXX
  /**
   * Convert this Memory to a Buffer.
   * The <i>start</i>, <i>position</i> and <i>end</i> are set to zero, zero, and <i>capacity</i>,
   * respectively.
   * @return Buffer
   */
  public abstract Buffer asBuffer();

  //ACCESS PRIMITIVE HEAP ARRAYS for readOnly XXX
  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final boolean[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.BOOLEAN, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final byte[] arr) {
    return wrap(arr, ByteOrder.nativeOrder());
  }

  /**
   * Wraps the given primitive array for read operations, with the given byte order.
   * @param arr the given primitive array
   * @param byteOrder the byte order
   * @return Memory for read operations
   */
  public static Memory wrap(final byte[] arr, final ByteOrder byteOrder) {
    return wrap(arr, 0, arr.length, byteOrder);
  }

  /**
   * Wraps the given primitive array for read operations assuming the given byte order.
   * @param arr the given primitive array
   * @param byteOrder the byte order
   * @return Memory for read operations
   */
  public static Memory wrap(final byte[] arr, final ByteOrder byteOrder) {
      return wrap(arr, 0, arr.length, byteOrder);
  }

  /**
   * Wraps the given primitive array for read operations assuming the given byte order.
   * @param arr the given primitive array
   * @param offset the byte offset into the given array
   * @param length the number of bytes to include from the given array
   * @param byteOrder the byte order
   * @return Memory for read operations
   */
  public static Memory wrap(final byte[] arr, final int offset, final int length,
          final ByteOrder byteOrder) {
      nullCheck(arr);
      nullCheck(byteOrder);
      UnsafeUtil.checkBounds(offset, length, arr.length);
      if (length == 0) {
          return WritableMemoryImpl.ZERO_SIZE_MEMORY;
      }
      final ResourceState state = new ResourceState(arr, Prim.BYTE, length);
      state.putRegionOffset(offset);
      state.order(byteOrder);
      return new WritableMemoryImpl(state);
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final char[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.CHAR, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final short[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.SHORT, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final int[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.INT, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final long[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.LONG, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final float[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.FLOAT, arr.length));
  }

  /**
   * Wraps the given primitive array for read operations assuming native byte order.
   * @param arr the given primitive array
   * @return Memory for read operations
   */
  public static Memory wrap(final double[] arr) {
    nullCheck(arr);
    if (arr.length == 0) {
      return WritableMemoryImpl.ZERO_SIZE_MEMORY;
    }
    return new WritableMemoryImpl(new ResourceState(arr, Prim.DOUBLE, arr.length));
  }

  //PRIMITIVE getXXX() and getXXXArray() XXX
  /**
   * Gets the boolean value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the boolean at the given offset
   */
  public abstract boolean getBoolean(long offsetBytes);

  /**
   * Gets the boolean array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getBooleanArray(long offsetBytes, boolean[] dstArray, int dstOffset,
      int length);

  /**
   * Gets the byte value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the byte at the given offset
   */
  public abstract byte getByte(long offsetBytes);

  /**
   * Gets the byte array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getByteArray(long offsetBytes, byte[] dstArray, int dstOffset,
      int length);

  /**
   * Gets the char value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the char at the given offset
   */
  public abstract char getChar(long offsetBytes);

  /**
   * Gets the char array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getCharArray(long offsetBytes, char[] dstArray, int dstOffset,
      int length);

  /**
   * Gets UTF-8 encoded bytes from this Memory, starting at offsetBytes to a length of
   * utf8LengthBytes, decodes them into characters and appends them to the given Appendable.
   * This is specifically designed to reduce the production of intermediate objects (garbage),
   * thus significantly reducing pressure on the JVM Garbage Collector.
   * @param offsetBytes offset bytes relative to the Memory start
   * @param utf8LengthBytes the number of encoded UTF-8 bytes to decode. It is assumed that the
   * caller has the correct number of utf8 bytes required to decode the number of characters
   * to be appended to dst. Characters outside the ASCII range can require 2, 3 or 4 bytes per
   * character to decode.
   * @param dst the destination Appendable to append decoded characters to
   * @throws IOException if dst.append() throws IOException
   * @throws Utf8CodingException in case of malformed or illegal UTF-8 input
   */
  public abstract void getCharsFromUtf8(long offsetBytes, int utf8LengthBytes, Appendable dst)
      throws IOException, Utf8CodingException;

  /**
   * Gets UTF-8 encoded bytes from this Memory, starting at offsetBytes to a length of
   * utf8LengthBytes, decodes them into characters and appends them to the given StringBuilder.
   * This method does *not* reset the length of the destination StringBuilder before appending
   * characters to it.
   * This is specifically designed to reduce the production of intermediate objects (garbage),
   * thus significantly reducing pressure on the JVM Garbage Collector.
   * @param offsetBytes offset bytes relative to the Memory start
   * @param utf8LengthBytes the number of encoded UTF-8 bytes to decode. It is assumed that the
   * caller has the correct number of utf8 bytes required to decode the number of characters
   * to be appended to dst. Characters outside the ASCII range can require 2, 3 or 4 bytes per
   * character to decode.
   * @param dst the destination StringBuilder to append decoded characters to
   * @throws Utf8CodingException in case of malformed or illegal UTF-8 input
   */
  public void getCharsFromUtf8(final long offsetBytes, final int utf8LengthBytes,
      final StringBuilder dst) throws Utf8CodingException {
    try {
      // Ensure that we do at most one resize of internal StringBuilder's char array
      dst.ensureCapacity(dst.length() + utf8LengthBytes);
      getCharsFromUtf8(offsetBytes, utf8LengthBytes, (Appendable) dst);
    } catch (final IOException e) {
      throw new RuntimeException("Should not happen", e);
    }
  }

  /**
   * Gets the double value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the double at the given offset
   */
  public abstract double getDouble(long offsetBytes);

  /**
   * Gets the double array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getDoubleArray(long offsetBytes, double[] dstArray, int dstOffset,
      int length);

  /**
   * Gets the float value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the float at the given offset
   */
  public abstract float getFloat(long offsetBytes);

  /**
   * Gets the float array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getFloatArray(long offsetBytes, float[] dstArray, int dstOffset,
      int length);

  /**
   * Gets the int value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the int at the given offset
   */
  public abstract int getInt(long offsetBytes);

  /**
   * Gets the int array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getIntArray(long offsetBytes, int[] dstArray, int dstOffset,
      int length);

  /**
   * Gets the long value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the long at the given offset
   */
  public abstract long getLong(long offsetBytes);

  /**
   * Gets the long array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getLongArray(long offsetBytes, long[] dstArray, int dstOffset, int length);

  /**
   * Gets the short value at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @return the short at the given offset
   */
  public abstract short getShort(long offsetBytes);

  /**
   * Gets the short array at the given offset
   * @param offsetBytes offset bytes relative to this Memory start
   * @param dstArray The preallocated destination array.
   * @param dstOffset offset in array units
   * @param length number of array units to transfer
   */
  public abstract void getShortArray(long offsetBytes, short[] dstArray, int dstOffset,
      int length);

  //OTHER PRIMITIVE READ METHODS: compareTo, copyTo XXX
  /**
   * Compares the bytes of this Memory to <i>that</i> Memory.
   * Returns <i>(this &lt; that) ? -1 : (this &gt; that) ? 1 : 0;</i>.
   * If all bytes are equal up to the shorter of the two lengths, the shorter length is considered
   * to be less than the other.
   * @param thisOffsetBytes the starting offset for <i>this Memory</i>
   * @param thisLengthBytes the length of the region to compare from <i>this Memory</i>
   * @param that the other Memory to compare with
   * @param thatOffsetBytes the starting offset for <i>that Memory</i>
   * @param thatLengthBytes the length of the region to compare from <i>that Memory</i>
   * @return <i>(this &lt; that) ? -1 : (this &gt; that) ? 1 : 0;</i>
   */
  public abstract int compareTo(long thisOffsetBytes, long thisLengthBytes, Memory that,
      long thatOffsetBytes, long thatLengthBytes);

  /**
   * Copies bytes from a source range of this Memory to a destination range of the given Memory
   * using the same low-level system copy function as found in
   * {@link java.lang.System#arraycopy(Object, int, Object, int, int)}.
   * @param srcOffsetBytes the source offset for this Memory
   * @param destination the destination Memory, which may not be Read-Only.
   * @param dstOffsetBytes the destination offset
   * @param lengthBytes the number of bytes to copy
   */
  public abstract void copyTo(long srcOffsetBytes, WritableMemory destination, long dstOffsetBytes,
      long lengthBytes);

  //OTHER READ METHODS XXX
  public abstract void checkValidAndBounds(long offset, long length);

  /**
   * Gets the capacity of this Memory in bytes
   * @return the capacity of this Memory in bytes
   */
  public abstract long getCapacity();

  /**
   * Returns the cumulative offset in bytes of this Memory from the backing resource
   * including the given offsetBytes and including the Java object header, if any.
   *
   * @param offsetBytes the given offset in bytes
   * @return the cumulative offset in bytes of this Memory including the given offsetBytes.
   */
  public abstract long getCumulativeOffset(final long offsetBytes);

  /**
   * Returns the ByteOrder for the backing resource.
   * @return the ByteOrder for the backing resource.
   */
  public abstract ByteOrder getResourceOrder();

  /**
   * Returns true if this Memory is backed by an on-heap primitive array
   * @return true if this Memory is backed by an on-heap primitive array
   */
  public abstract boolean hasArray();

  /**
   * Returns true if this Memory is backed by a ByteBuffer
   * @return true if this Memory is backed by a ByteBuffer
   */
  public abstract boolean hasByteBuffer();

  /**
   * Returns true if the backing memory is direct (off-heap) memory.
   * @return true if the backing memory is direct (off-heap) memory.
   */
  public abstract boolean isDirect();

  /**
   * Returns true if the backing resource is read only
   * @return true if the backing resource is read only
   */
  public abstract boolean isResourceReadOnly();

  /**
   * Returns true if the backing resource of <i>this</i> is identical with the backing resource
   * of <i>that</i>. If the backing resource is a heap array or ByteBuffer, the offset and
   * capacity must also be identical.
   * @param that A different given Memory object
   * @return true if the backing resource of <i>this</i> is identical with the backing resource
   * of <i>that</i>.
   */
  public abstract boolean isSameResource(Memory that);

  /**
   * Returns true if this Memory is valid() and has not been closed.
   * @return true if this Memory is valid() and has not been closed.
   */
  public abstract boolean isValid();

  /**
   * Return true if bytes need to be swapped based on resource ByteOrder.
   * @return true if bytes need to be swapped based on resource ByteOrder.
   */
  public abstract boolean swapBytes();

  /**
   * Returns a formatted hex string of a range of this Memory.
   * Used primarily for testing.
   * @param header descriptive header
   * @param offsetBytes offset bytes relative to this Memory start
   * @param lengthBytes number of bytes to convert to a hex string
   * @return a formatted hex string in a human readable array
   */
  public abstract String toHexString(String header, long offsetBytes, int lengthBytes);

  /**
   * Returns a formatted hex string of an area of this Memory.
   * Used primarily for testing.
   * @param preamble a descriptive header
   * @param offsetBytes offset bytes relative to the Memory start
   * @param lengthBytes number of bytes to convert to a hex string
   * @param state the ResourceState
   * @return a formatted hex string in a human readable array
   */
  static String toHex(final String preamble, final long offsetBytes, final int lengthBytes,
      final ResourceState state) {
    UnsafeUtil.checkBounds(offsetBytes, lengthBytes, state.getCapacity());
    final StringBuilder sb = new StringBuilder();
    final Object uObj = state.getUnsafeObject();
    final String uObjStr = (uObj == null) ? "null"
            : uObj.getClass().getSimpleName() + ", " + (uObj.hashCode() & 0XFFFFFFFFL);
    final ByteBuffer bb = state.getByteBuffer();
    final String bbStr = (bb == null) ? "null"
            : bb.getClass().getSimpleName() + ", " + (bb.hashCode() & 0XFFFFFFFFL);
    final MemoryRequestServer memReqSvr = state.getMemoryRequestServer();
    final String memReqStr = (memReqSvr == null) ? "null"
            : memReqSvr.getClass().getSimpleName() + ", " + (memReqSvr.hashCode() & 0XFFFFFFFFL);
    final long cumBaseOffset = state.getCumBaseOffset();
    sb.append(preamble).append(LS);
    sb.append("NativeBaseOffset    : ").append(state.getNativeBaseOffset()).append(LS);
    sb.append("UnsafeObj, hashCode : ").append(uObjStr).append(LS);
    sb.append("UnsafeObjHeader     : ").append(state.getUnsafeObjectHeader()).append(LS);
    sb.append("ByteBuf, hashCode   : ").append(bbStr).append(LS);
    sb.append("RegionOffset        : ").append(state.getRegionOffset()).append(LS);
    sb.append("Capacity            : ").append(state.getCapacity()).append(LS);
    sb.append("CumBaseOffset       : ").append(cumBaseOffset).append(LS);
    sb.append("MemReq, hashCode    : ").append(memReqStr).append(LS);
    sb.append("Valid               : ").append(state.isValid()).append(LS);
    sb.append("Resource Read Only  : ").append(state.isResourceReadOnly()).append(LS);
    sb.append("Resource Endianness : ").append(state.order().toString()).append(LS);
    sb.append("JDK Version         : ").append(UnsafeUtil.JDK).append(LS);
    //Data detail
    sb.append("Data, littleEndian  :  0  1  2  3  4  5  6  7");

    for (long i = 0; i < lengthBytes; i++) {
      final int b = unsafe.getByte(uObj, cumBaseOffset + offsetBytes + i) & 0XFF;
      if ((i % 8) == 0) { //row header
        sb.append(String.format("%n%20s: ", offsetBytes + i));
      }
      sb.append(String.format("%02x ", b));
    }
    sb.append(LS);

    return sb.toString();
  }

  //MONITORING
  /**
   * Gets the current number of active direct memory allocations.
   * @return the current number of active direct memory allocations.
   */
  public static long getCurrentDirectMemoryAllocations() {
    return ResourceState.currentDirectMemoryAllocations_.get();
  }

  /**
   * Gets the current size of active direct memory allocated.
   * @return the current size of active direct memory allocated.
   */
  public static long getCurrentDirectMemoryAllocated() {
    return ResourceState.currentDirectMemoryAllocated_.get();
  }

  /**
   * Gets the current number of active direct memory map allocations.
   * @return the current number of active direct memory map allocations.
   */
  public static long getCurrentDirectMemoryMapAllocations() {
    return ResourceState.currentDirectMemoryMapAllocations_.get();
  }

  /**
   * Gets the current size of active direct memory map allocated.
   * @return the current size of active direct memory map allocated.
   */
  public static long getCurrentDirectMemoryMapAllocated() {
    return ResourceState.currentDirectMemoryMapAllocated_.get();
  }

  abstract ResourceState getResourceState();

}
