package test;

import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.ibm.integration.test.v1.NodeSpy;
import com.ibm.integration.test.v1.SpyObjectReference;
import com.ibm.integration.test.v1.TestMessageAssembly;
import com.ibm.integration.test.v1.TestSetup;
import com.ibm.integration.test.v1.exception.TestException;

import static com.ibm.integration.test.v1.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class MySimpleApplication_MyMessageFlowWithTwoComputeNodes_IntegrationTest {

	@AfterEach
	public void cleanupTest() throws TestException {
		// Ensure any mocks created by a test are cleared after the test runs 
		TestSetup.restoreAllMocks();
	}

	@Test
	public void MySimpleApplication_MyMessageFlowWithTwoComputeNodes_TestCase_001() throws TestException {

		// Define the SpyObjectReference for Compute1 and Compute2
		SpyObjectReference SpyCompute1Reference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("Compute1");
		SpyObjectReference SpyCompute2Reference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("Compute2");

		// Initialise a NodeSpy
		NodeSpy nodeSpyCompute1 = new NodeSpy(SpyCompute1Reference);
		NodeSpy nodeSpyCompute2 = new NodeSpy(SpyCompute2Reference);

		// Declare a new TestMessageAssembly object for the message being sent into the node
		TestMessageAssembly inputMessageAssembly = new TestMessageAssembly();

		// Create a Message Assembly from the input data file
		try {
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute1_input.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			inputMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}

		// Call the message flow node Compute1 with the Message Assembly
		// NOTE singleNodeTest is true so message propagation to Compute2 will not occur!
		nodeSpyCompute1.evaluate(inputMessageAssembly, true, "in"); 
		// Assert that Compute1 is called 1 time
		assertThat(nodeSpyCompute1, nodeCallCountIs(1));
		// Assert that Compute1 output terminal propagate count is 1
		assertThat(nodeSpyCompute1, terminalPropagateCountIs("out", 1));
		// Assert that Compute2 is called 0 times
		assertThat(nodeSpyCompute2, nodeCallCountIs(0));
		// Assert that Compute2 output terminal propagate count is 1
		assertThat(nodeSpyCompute2, terminalPropagateCountIs("out", 0));

		// Compare the output message from Compute1
		try {
			TestMessageAssembly actualMessageAssemblyCompute1 = null;
			TestMessageAssembly expectedMessageAssemblyCompute1 = null;
			// Get the TestMessageAssembly object for the actual propagated message
			actualMessageAssemblyCompute1 = nodeSpyCompute1.propagatedMessageAssembly("out", 1);
			// Get the TestMessageAssembly object for the expected propagated message
			expectedMessageAssemblyCompute1 = new TestMessageAssembly();
			// Create a Message Assembly from the expected output mxml resource
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute1_output.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			expectedMessageAssemblyCompute1.buildFromRecordedMessageAssembly(messageStream);
			// Assert that the actual message tree matches the expected message tree
			assertThat(actualMessageAssemblyCompute1, equalsMessage(expectedMessageAssemblyCompute1));
		} catch (Exception ex) {
			throw new TestException("Compute1 - Failed to compare with expected message", ex);
		}		
	}

	@Test
	public void MySimpleApplication_MyMessageFlowWithTwoComputeNodes_TestCase_002() throws TestException {

		// Define the SpyObjectReference for Compute1 and Compute2
		SpyObjectReference SpyCompute1Reference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("Compute1");
		SpyObjectReference SpyCompute2Reference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("Compute2");

		// Initialise a NodeSpy
		NodeSpy nodeSpyCompute1 = new NodeSpy(SpyCompute1Reference);
		NodeSpy nodeSpyCompute2 = new NodeSpy(SpyCompute2Reference);

		// Declare a new TestMessageAssembly object for the message being sent into the node
		TestMessageAssembly inputMessageAssembly = new TestMessageAssembly();

		// Create a Message Assembly from the input data file
		try {
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute1_input.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			inputMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}

		// Call the message flow node Compute1 with the Message Assembly
		// NOTE singleNodeTest is false so message propagation to Compute2 will occur!
		nodeSpyCompute1.evaluate(inputMessageAssembly, false, "in"); 
		// Assert that Compute1 is called 1 time
		assertThat(nodeSpyCompute1, nodeCallCountIs(1));
		// Assert that Compute1 output terminal propagate count is 1
		assertThat(nodeSpyCompute1, terminalPropagateCountIs("out", 1));
		// Assert that Compute2 is called 1 time
		assertThat(nodeSpyCompute2, nodeCallCountIs(1));
		// Assert that Compute2 output terminal propagate count is 1
		assertThat(nodeSpyCompute2, terminalPropagateCountIs("out", 1));

		// Compare the output message from Compute1
		try {
			TestMessageAssembly actualMessageAssemblyCompute1 = null;
			TestMessageAssembly expectedMessageAssemblyCompute1 = null;
			// Get the TestMessageAssembly object for the actual propagated message
			actualMessageAssemblyCompute1 = nodeSpyCompute1.propagatedMessageAssembly("out", 1);
			// Get the TestMessageAssembly object for the expected propagated message
			expectedMessageAssemblyCompute1 = new TestMessageAssembly();
			// Create a Message Assembly from the expected output mxml resource
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute1_output.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			expectedMessageAssemblyCompute1.buildFromRecordedMessageAssembly(messageStream);
			// Assert that the actual message tree matches the expected message tree
			assertThat(actualMessageAssemblyCompute1, equalsMessage(expectedMessageAssemblyCompute1));
		} catch (Exception ex) {
			throw new TestException("Compute1 - Failed to compare with expected message", ex);
		}		
		
		// Compare the output message from Compute2
		try {
			TestMessageAssembly actualMessageAssemblyCompute2 = null;
			TestMessageAssembly expectedMessageAssemblyCompute2 = null;
			// Get the TestMessageAssembly object for the actual propagated message
			actualMessageAssemblyCompute2 = nodeSpyCompute2.propagatedMessageAssembly("out", 1);
			// Get the TestMessageAssembly object for the expected propagated message
			expectedMessageAssemblyCompute2 = new TestMessageAssembly();
			// Create a Message Assembly from the expected output mxml resource
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute2_output.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			expectedMessageAssemblyCompute2.buildFromRecordedMessageAssembly(messageStream);
			// Assert that the actual message tree matches the expected message tree
			assertThat(actualMessageAssemblyCompute2, equalsMessage(expectedMessageAssemblyCompute2));
		} catch (Exception ex) {
			throw new TestException("Compute2 - Failed to compare with expected message", ex);
		}		
		
	}

	@Test
	public void MySimpleApplication_MyMessageFlowWithTwoComputeNodes_TestCase_003() throws TestException {

		// Define the SpyObjectReference for HTTPInput and HTTPReply
		SpyObjectReference SpyHTTPInputReference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("HTTP Input");
		SpyObjectReference SpyHTTPReplyReference = new SpyObjectReference().application("MySimpleApplication").messageFlow("MyMessageFlowWithTwoComputeNodes").node("HTTP Reply");

		// Initialise a NodeSpy
		NodeSpy nodeSpyHTTPInput = new NodeSpy(SpyHTTPInputReference);
		NodeSpy nodeSpyHTTPReply = new NodeSpy(SpyHTTPReplyReference);
		// Set up a stop at the in terminal of the NodeSpy for the HTTPReply
		nodeSpyHTTPReply.setStopAtInputTerminal("in");		
		
		// Declare a new TestMessageAssembly object for the message being sent into the node
		TestMessageAssembly inputMessageAssembly = new TestMessageAssembly();

		// Create a Message Assembly from the input data file
		try {
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute1_input.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			inputMessageAssembly.buildFromRecordedMessageAssembly(messageStream);
		} catch (Exception ex) {
			throw new TestException("Failed to load input message", ex);
		}

		// Call the message flow node HTTP Input with the Message Assembly
		// Use the propagate method to send the Message Assembly to the out terminal of the HTTPInput node
		// Treat Compute1 and Compute2 as a "black box" for this test, then check the outcome at the in terminal of HTTPReply 
		nodeSpyHTTPInput.propagate(inputMessageAssembly, "out"); 
		// Demonstrate that the HTTPReply has not been called due to the earlier setStopAtInputTerminal("in")
		assertThat(nodeSpyHTTPReply, nodeCallCountIs(0));

		// Compare the message arriving at the in terminal of HTTPReply
		try {
			TestMessageAssembly actualMessageAssemblyHTTPReply = null;
			TestMessageAssembly expectedMessageAssemblyHTTPReply = null;
			// Get the TestMessageAssembly object for the actual message
			actualMessageAssemblyHTTPReply = nodeSpyHTTPReply.receivedMessageAssembly("in", 1);
			// Get the TestMessageAssembly object for the expected message
			expectedMessageAssemblyHTTPReply = new TestMessageAssembly();
			// Create a Message Assembly from the expected output mxml resource
			String messageAssemblyPath = "/MyMessageFlowWithTwoComputeNodes_Compute2_output.mxml";
			InputStream messageStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(messageAssemblyPath);
			if (messageStream == null) {
				throw new TestException("Unable to locate message assembly file: " + messageAssemblyPath);
			}
			expectedMessageAssemblyHTTPReply.buildFromRecordedMessageAssembly(messageStream);
			// Assert that the actual message tree matches the expected message tree
			assertThat(actualMessageAssemblyHTTPReply, equalsMessage(expectedMessageAssemblyHTTPReply));
		} catch (Exception ex) {
			throw new TestException("HTTP Reply - Failed to compare with expected message", ex);
		}		
		
	}

}
