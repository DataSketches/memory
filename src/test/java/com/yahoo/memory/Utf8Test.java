/*
 * Copyright 2017, Yahoo! Inc. Licensed under the terms of the
 * Apache License 2.0. See LICENSE file at the project root for terms.
 */

package com.yahoo.memory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import com.google.protobuf.ByteString;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Adapted version of
 * https://github.com/google/protobuf/blob/3e944aec9ebdf5043780fba751d604c0a55511f2/
 * java/core/src/test/java/com/google/protobuf/DecodeUtf8Test.java
 *
 * Copyright 2008 Google Inc.  All rights reserved.
 * https://developers.google.com/protocol-buffers/
 */
public class Utf8Test {
  private static Logger logger = Logger.getLogger(Utf8Test.class.getName());

  @Test
  public void testRoundTripAllValidChars() {
    for (int i = Character.MIN_CODE_POINT; i < Character.MAX_CODE_POINT; i++) {
      if (i < Character.MIN_SURROGATE || i > Character.MAX_SURROGATE) {
        String str = new String(Character.toChars(i));
        assertRoundTrips(str);
      }
    }
  }

  @Test
  public void testPutInvalidChars() {
    WritableMemory mem = WritableMemory.allocate(10);
    WritableMemory emptyMem = WritableMemory.allocate(0);
    for (int i = Character.MIN_SURROGATE; i <= Character.MAX_SURROGATE; i++) {
      assertSurrogate(mem, (char) i);
      assertSurrogate(emptyMem, (char) i);
    }
  }

  private void assertSurrogate(WritableMemory mem, char c) {
    try {
      mem.putUtf8(0, new String(new char[] {c}));
      fail();
    } catch (WritableMemoryImpl.UnpairedSurrogateException e) {
      // Expected.
    }
  }

  // Test all 1, 2, 3 invalid byte combinations. Valid ones would have been covered above.

  @Test
  public void testOneByte() {
    int valid = 0;
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
      ByteString bs = ByteString.copyFrom(new byte[] {(byte) i });
      if (!bs.isValidUtf8()) {
        assertInvalid(bs.toByteArray());
      } else {
        valid++;
      }
    }
    assertEquals(IsValidUtf8TestUtil.EXPECTED_ONE_BYTE_ROUNDTRIPPABLE_COUNT, valid);
  }

  @Test
  public void testTwoBytes() {
    int valid = 0;
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
      for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; j++) {
        ByteString bs = ByteString.copyFrom(new byte[]{(byte) i, (byte) j});
        if (!bs.isValidUtf8()) {
          assertInvalid(bs.toByteArray());
        } else {
          valid++;
        }
      }
    }
    assertEquals(IsValidUtf8TestUtil.EXPECTED_TWO_BYTE_ROUNDTRIPPABLE_COUNT, valid);
  }

  @Test
  public void testThreeBytes() {
    // Travis' OOM killer doesn't like this test
    if (System.getenv("TRAVIS") == null) {
      int count = 0;
      int valid = 0;
      for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
        for (int j = Byte.MIN_VALUE; j <= Byte.MAX_VALUE; j++) {
          for (int k = Byte.MIN_VALUE; k <= Byte.MAX_VALUE; k++) {
            byte[] bytes = new byte[]{(byte) i, (byte) j, (byte) k};
            ByteString bs = ByteString.copyFrom(bytes);
            if (!bs.isValidUtf8()) {
              assertInvalid(bytes);
            } else {
              valid++;
            }
            count++;
            if (count % 1000000L == 0) {
              logger.info("Processed " + (count / 1000000L) + " million characters");
            }
          }
        }
      }
      assertEquals(IsValidUtf8TestUtil.EXPECTED_THREE_BYTE_ROUNDTRIPPABLE_COUNT, valid);
    }
  }

  /**
   * Tests that round tripping of a sample of four byte permutations work.
   */
  @Test
  public void testInvalid_4BytesSamples() {
    // Bad trailing bytes
    assertInvalid(0xF0, 0xA4, 0xAD, 0x7F);
    assertInvalid(0xF0, 0xA4, 0xAD, 0xC0);

    // Special cases for byte2
    assertInvalid(0xF0, 0x8F, 0xAD, 0xA2);
    assertInvalid(0xF4, 0x90, 0xAD, 0xA2);
  }

  @Test
  public void testRealStrings() {
    // English
    assertRoundTrips("The quick brown fox jumps over the lazy dog");
    // German
    assertRoundTrips("Quizdeltagerne spiste jordb\u00e6r med fl\u00f8de, mens cirkusklovnen");
    // Japanese
    assertRoundTrips(
        "\u3044\u308d\u306f\u306b\u307b\u3078\u3068\u3061\u308a\u306c\u308b\u3092");
    // Hebrew
    assertRoundTrips(
        "\u05d3\u05d2 \u05e1\u05e7\u05e8\u05df \u05e9\u05d8 \u05d1\u05d9\u05dd "
        + "\u05de\u05d0\u05d5\u05db\u05d6\u05d1 \u05d5\u05dc\u05e4\u05ea\u05e2"
        + " \u05de\u05e6\u05d0 \u05dc\u05d5 \u05d7\u05d1\u05e8\u05d4 "
        + "\u05d0\u05d9\u05da \u05d4\u05e7\u05dc\u05d9\u05d8\u05d4");
    // Thai
    assertRoundTrips(
        " \u0e08\u0e07\u0e1d\u0e48\u0e32\u0e1f\u0e31\u0e19\u0e1e\u0e31\u0e12"
        + "\u0e19\u0e32\u0e27\u0e34\u0e0a\u0e32\u0e01\u0e32\u0e23");
    // Chinese
    assertRoundTrips(
        "\u8fd4\u56de\u94fe\u4e2d\u7684\u4e0b\u4e00\u4e2a\u4ee3\u7406\u9879\u9009\u62e9\u5668");
    // Chinese with 4-byte chars
    assertRoundTrips("\uD841\uDF0E\uD841\uDF31\uD841\uDF79\uD843\uDC53\uD843\uDC78"
                     + "\uD843\uDC96\uD843\uDCCF\uD843\uDCD5\uD843\uDD15\uD843\uDD7C\uD843\uDD7F"
                     + "\uD843\uDE0E\uD843\uDE0F\uD843\uDE77\uD843\uDE9D\uD843\uDEA2");
    // Mixed
    assertRoundTrips(
        "The quick brown \u3044\u308d\u306f\u306b\u307b\u3078\u8fd4\u56de\u94fe"
        + "\u4e2d\u7684\u4e0b\u4e00");
  }

  @Test
  public void testOverlong() {
    assertInvalid(0xc0, 0xaf);
    assertInvalid(0xe0, 0x80, 0xaf);
    assertInvalid(0xf0, 0x80, 0x80, 0xaf);

    // Max overlong
    assertInvalid(0xc1, 0xbf);
    assertInvalid(0xe0, 0x9f, 0xbf);
    assertInvalid(0xf0 ,0x8f, 0xbf, 0xbf);

    // null overlong
    assertInvalid(0xc0, 0x80);
    assertInvalid(0xe0, 0x80, 0x80);
    assertInvalid(0xf0, 0x80, 0x80, 0x80);
  }

  @Test
  public void testIllegalCodepoints() {
    // Single surrogate
    assertInvalid(0xed, 0xa0, 0x80);
    assertInvalid(0xed, 0xad, 0xbf);
    assertInvalid(0xed, 0xae, 0x80);
    assertInvalid(0xed, 0xaf, 0xbf);
    assertInvalid(0xed, 0xb0, 0x80);
    assertInvalid(0xed, 0xbe, 0x80);
    assertInvalid(0xed, 0xbf, 0xbf);

    // Paired surrogates
    assertInvalid(0xed, 0xa0, 0x80, 0xed, 0xb0, 0x80);
    assertInvalid(0xed, 0xa0, 0x80, 0xed, 0xbf, 0xbf);
    assertInvalid(0xed, 0xad, 0xbf, 0xed, 0xb0, 0x80);
    assertInvalid(0xed, 0xad, 0xbf, 0xed, 0xbf, 0xbf);
    assertInvalid(0xed, 0xae, 0x80, 0xed, 0xb0, 0x80);
    assertInvalid(0xed, 0xae, 0x80, 0xed, 0xbf, 0xbf);
    assertInvalid(0xed, 0xaf, 0xbf, 0xed, 0xb0, 0x80);
    assertInvalid(0xed, 0xaf, 0xbf, 0xed, 0xbf, 0xbf);
  }

  @Test
  public void testBufferSlice() {
    String str = "The quick brown fox jumps over the lazy dog";
    assertRoundTrips(str, 10, 4);
    assertRoundTrips(str, str.length(), 0);
  }

  @Test
  public void testInvalidBufferSlice() {
    byte[] bytes  = "The quick brown fox jumps over the lazy dog".getBytes(StandardCharsets.UTF_8);
    assertInvalidSlice(bytes, bytes.length - 3, 4);
    assertInvalidSlice(bytes, bytes.length, 1);
    assertInvalidSlice(bytes, bytes.length + 1, 0);
    assertInvalidSlice(bytes, 0, bytes.length + 1);
  }

  private void assertInvalid(int... bytesAsInt) {
    byte[] bytes = new byte[bytesAsInt.length];
    for (int i = 0; i < bytesAsInt.length; i++) {
      bytes[i] = (byte) bytesAsInt[i];
    }
    assertInvalid(bytes);
  }

  private void assertInvalid(byte[] bytes) {
    try {
      Memory.wrap(bytes).getUtf8(0, new StringBuilder(), bytes.length);
      fail();
    } catch (Utf8CodingException e) {
      // Expected.
    }
  }

  private void assertInvalidSlice(byte[] bytes, int index, int size) {
    try {
      Memory.wrap(bytes).getUtf8(index, new StringBuilder(), size);
      fail();
    } catch (IllegalArgumentException e) {
      // Expected.
    }
  }

  private void assertRoundTrips(String str) {
    assertRoundTrips(str, 0, -1);
  }

  private void assertRoundTrips(String str, int index, int size) {
    byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
    if (size == -1) {
      size = bytes.length;
    }
    StringBuilder sb = new StringBuilder();

    Memory.wrap(bytes).getUtf8(index, sb, size);
    assertDecode(new String(bytes, index, size, StandardCharsets.UTF_8), sb.toString());

    WritableMemory writeMem = WritableMemory.allocate(bytes.length);
    writeMem.putUtf8(0, str);
    assertEquals(0, writeMem.compareTo(0, bytes.length, Memory.wrap(bytes), 0, bytes.length));

    // Test write overflow
    WritableMemory writeMem2 = WritableMemory.allocate(bytes.length - 1);
    try {
      writeMem2.putUtf8(0, str);
      fail();
    } catch (IllegalArgumentException e) {
      // Expected.
    }
  }

  private void assertDecode(String expected, String actual) {
    if (!expected.equals(actual)) {
      fail("Failure: Expected (" + codepoints(expected) + ") Actual (" + codepoints(actual) + ")");
    }
  }

  private List<String> codepoints(String str) {
    List<String> codepoints = new ArrayList<>();
    for (int i = 0; i < str.length(); i++) {
      codepoints.add(Long.toHexString(str.charAt(i)));
    }
    return codepoints;
  }

}
