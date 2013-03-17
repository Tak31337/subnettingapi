/*******************************************************************************
 * Copyright (c) 2010 Hippos Development Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.opensource.org/licenses/eclipse-1.0.php
 * Contributors:
 *
 *    Hippos Development Team - Java Subnetting Calculator
 *
 ******************************************************************************/
package com.taksmind.tests;

import com.taksmind.subnet.Subnet;
import com.taksmind.subnet.util.ANDing;
import com.taksmind.subnet.util.Conversion;

import junit.framework.TestCase;

/**
 * JUnit tests for the subnetting api.
 * @author Lance Colton
 * @since 1.2
 */
public class SubnetTest extends TestCase {

	/**
	 * Test of binary conversion.
	 * @since 1.0
	 */
	public void testBinaryConversion() {
		String decimal = "4";
		String expected = "00000100";
		String result = Conversion.toBinary(decimal);
		assertEquals(expected, result);
	}

	/**
	 * Test of decimal conversion.
	 * @since 1.0
	 */
	public void testDecimalConversion() {
		String binary = "00000100";
		String expected = "4";
		String result = Conversion.toDecimal(binary);
		assertEquals(expected, result);
	}

	/**
	 * Test of ip to string conversion.
	 * @since 1.0
	 */
	public void testIpToStringConversion() {
		int a = 111;
		int b = 111;
		int c = 111;
		int d = 111;

		String expected = "111.111.111.111";
		String result = Conversion.ipToString(a, b, c, d);
		assertEquals(expected, result);
	}

	/**
	 * Test of class assignment.
	 * @since 1.0
	 */
	public void testClassAssignment() {
		Subnet subnetTest = new Subnet();
		subnetTest.setIPAddress("10.0.0.0");
		assertEquals('a', subnetTest.getNetworkClass());

		subnetTest.setIPAddress("128.1.0.1");
		assertEquals('b', subnetTest.getNetworkClass());

		subnetTest.setIPAddress("192.0.1.1");
		assertEquals('c', subnetTest.getNetworkClass());

		subnetTest.setIPAddress("224.0.0.0");
		assertEquals('d', subnetTest.getNetworkClass());

		subnetTest.setIPAddress("240.0.0.0");
		assertEquals('e', subnetTest.getNetworkClass());
	}

	/**
	 * Test of ANDing.
	 * @since 1.0
	 */
	public void testANDing() {
		String ip1 = "140.179.220.200";
		String ip2 = "255.255.224.000";

		String expected = "140.179.192.0";
		String result = ANDing.and(ip1, ip2);

		assertEquals(expected, result);
	}

	/**
	 * Test of broadcast ANDing.
	 * @since 1.0
	 */
	public void testBroadcastANDing() {
		String ip1 = "140.179.192.0";
		String ip2 = "255.255.224.0";

		String expected = "140.179.223.255";
		String result = ANDing.broadcast(ip1, ip2);

		assertEquals(expected, result);
	}

	/**
	 * Test of calculate bit information.
	 * @since 1.0
	 */
	public void testCalculateBitInformation() {
		String ip1 = "255.255.224.0";
		String ip2 = "140.179.220.200";

		int expectedSubnetBits = 3;
		int expectedTotalSubnets = 8;
		int expectedUsableSubnets = 6;
		int expectedMaskedBits = 19;
		int expectedTotalHosts = 8192;
		int expectedUsableHosts = 8190;

		Subnet testSubnet = new Subnet();
		testSubnet.setIPAddress(ip2);
		testSubnet.setSubnetMask(ip1);

		int resultSubnetBits = testSubnet.getSubnetBits();
		int resultTotalSubnets = testSubnet.getTotalSubnets();
		int resultUsableSubnets = testSubnet.getUsableSubnets();
		int resultMaskedBits = testSubnet.getMaskedBits();
		int resultTotalHosts = testSubnet.getTotalHosts();
		int resultUsableHosts = testSubnet.getUsableHosts();

		assertEquals(expectedSubnetBits,    resultSubnetBits);
		assertEquals(expectedTotalSubnets,  resultTotalSubnets);
		assertEquals(expectedUsableSubnets, resultUsableSubnets);
		assertEquals(expectedMaskedBits,    resultMaskedBits);
		assertEquals(expectedTotalHosts,    resultTotalHosts);
		assertEquals(expectedUsableHosts,   resultUsableHosts);
	}

	/**
	 * Test host address range.
	 * @since 1.0
	 */
	public void testHostAddressRange() {
		String ip1 = "140.179.220.200";
		String ip2 = "255.255.224.000";

		String expectedHostMin = "140.179.192.1";
		String expectedHostMax = "140.179.223.254";

		Subnet testSubnet = new Subnet();
		testSubnet.setIPAddress(ip1);
		testSubnet.setSubnetMask(ip2);

		String resultHostMin = testSubnet.getMinimumHostAddressRange();
		String resultHostMax = testSubnet.getMaximumHostAddressRange();

		assertEquals(expectedHostMin, resultHostMin);
		assertEquals(expectedHostMax, resultHostMax);
	}

	/**
	 * Test set subnet bits.
	 * @since 1.0
	 */
	public void testSetSubnetBits() {
		String ip1 = "140.179.220.200";
		int subnetBits = 4;

		Subnet testSubnet = new Subnet();
		testSubnet.setIPAddress(ip1);
		testSubnet.setSubnetBits(subnetBits);

		String expectedSubnetMask = "255.255.240.0";
		String expectedSubnetAddress = "140.179.208.0";
		int expectedMaskBits = 20;
		int expectedTotalSubnets = 16;
		int expectedTotalHosts = 4096;

		String resultSubnetMask = testSubnet.getSubnetMask();
		String resultSubnetAddress = testSubnet.getSubnetAddress();
		int resultMaskBits = testSubnet.getMaskedBits();
		int resultTotalSubnets = testSubnet.getTotalSubnets();
		int resultTotalHosts = testSubnet.getTotalHosts();

		assertEquals(expectedSubnetMask,    resultSubnetMask);
		assertEquals(expectedSubnetAddress, resultSubnetAddress);
		assertEquals(expectedMaskBits,      resultMaskBits);
		assertEquals(expectedTotalSubnets,  resultTotalSubnets);
		assertEquals(expectedTotalHosts,    resultTotalHosts);
	}

	/**
	 * Test set total subnets.
	 * @since 1.0
	 */
	public void testSetTotalSubnets() {
		String ip1 = "140.179.220.200";
		int totalSubnets = 32;

		Subnet testSubnet = new Subnet();
		testSubnet.setIPAddress(ip1);
		testSubnet.setTotalSubnets(totalSubnets);

		String expectedSubnetMask = "255.255.248.0";
		String expectedSubnetAddress = "140.179.216.0";
		int expectedMaskBits = 21;
		int expectedSubnetBits = 5;
		int expectedTotalHosts = 2048;

		String resultSubnetMask = testSubnet.getSubnetMask();
		String resultSubnetAddress = testSubnet.getSubnetAddress();
		int resultMaskBits = testSubnet.getMaskedBits();
		int resultSubnetBits = testSubnet.getSubnetBits();
		int resultTotalHosts = testSubnet.getTotalHosts();

		assertEquals(expectedSubnetMask,    resultSubnetMask);
		assertEquals(expectedSubnetAddress, resultSubnetAddress);
		assertEquals(expectedMaskBits,      resultMaskBits);
		assertEquals(expectedSubnetBits,    resultSubnetBits);
		assertEquals(expectedTotalHosts,    resultTotalHosts);
	}

	/**
	 * Test set masked bits.
	 * @since 1.0
	 */
	public void testSetMaskedBits() {
		String ip1 = "140.179.220.200";
		int maskedBits = 19;

		Subnet testSubnet = new Subnet();
		testSubnet.setIPAddress(ip1);
		testSubnet.setMaskedBits(maskedBits);

		String expectedSubnetMask = "255.255.224.0";
		String expectedSubnetAddress = "140.179.192.0";
		int expectedTotalSubnets = 8;
		int expectedSubnetBits = 3;
		int expectedTotalHosts = 8192;

		String resultSubnetMask = testSubnet.getSubnetMask();
		String resultSubnetAddress = testSubnet.getSubnetAddress();
		int resultTotalSubnets = testSubnet.getTotalSubnets();
		int resultSubnetBits = testSubnet.getSubnetBits();
		int resultTotalHosts = testSubnet.getTotalHosts();

		assertEquals(expectedSubnetMask,    resultSubnetMask);
		assertEquals(expectedSubnetAddress, resultSubnetAddress);
		assertEquals(expectedTotalSubnets,  resultTotalSubnets);
		assertEquals(expectedSubnetBits,    resultSubnetBits);
		assertEquals(expectedTotalHosts,    resultTotalHosts);
	}

	/**
	 * Test set total hosts.
	 * @since 1.0
	 */
	public void testSetTotalHosts() {
		String ip1 = "140.179.220.200";
		int totalHosts = 16384;

		Subnet testHosts = new Subnet();
		testHosts.setIPAddress(ip1);
		testHosts.setTotalHosts(totalHosts);

		String expectedSubnetMask = "255.255.192.0";
		String expectedSubnetAddress = "140.179.192.0";
		int expectedMaskBits = 18;
		int expectedSubnetBits = 2;
		int expectedTotalSubnets = 4;

		String resultSubnetMask = testHosts.getSubnetMask();
		String resultSubnetAddress = testHosts.getSubnetAddress();
		int resultMaskBits = testHosts.getMaskedBits();
		int resultSubnetBits = testHosts.getSubnetBits();
		int resultTotalSubnets = testHosts.getTotalSubnets();

		assertEquals(expectedSubnetMask,    resultSubnetMask);
		assertEquals(expectedSubnetAddress, resultSubnetAddress);
		assertEquals(expectedMaskBits,      resultMaskBits);
		assertEquals(expectedSubnetBits,    resultSubnetBits);
		assertEquals(expectedTotalSubnets,  resultTotalSubnets);
	}

	/**
	 * Test ip to bin.
	 * @since 1.0
	 */
	public void testIpToBin() {
		String ip = "192.168.2.1";

		char[][] d = Conversion.ipToBin(ip);
		String result = new String(d[0]) + "." + new String(d[1]) + "."
				+ new String(d[2]) + "." + new String(d[3]);
		
		String expected = "11000000.10101000.00000010.00000001";
		
		assertEquals(expected, result);
	}
	
	/**
	 * Test network increment
	 * @since 1.2
	 */
	public void testNetIncrement() {
		String ip1 = "192.168.1.1";
		int totalHosts = 32;
		
		int expected = 32;

		Subnet testInc = new Subnet();
		testInc.setIPAddress(ip1);
		testInc.setTotalHosts(totalHosts);
		
		int result = testInc.getNetworkIncrement();
		
		assertEquals(expected, result);
	}
	
	/**
	 * Round to the highest exponent of 2
	 * @since 1.2
	 */
	public void testRounding() {
		int result = Conversion.numRound(500);
		int expected = 512;
		assertEquals(expected, result);
	}
}
