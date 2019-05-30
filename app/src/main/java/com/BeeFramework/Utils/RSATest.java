package com.BeeFramework.Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class RSATest
{
	//base64 code
	static String PUCLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgkD7ASE+jfTg3oBZwl0zn3G" +
			"bBXuvrR9G5ArmjbsT0OLh1rGcivizYHaoTqcc8eytHxCuL16uA2CcE7MLfnK8ZgLUMjvNNz3zJhuq9v0muOCJGw" +
			"qqyUEVZ7yAbFPA9r+jzcdpLOkoIeCZUa+/OMfGOAyCS/PdJKJhsFhcWv8NfbCSvn+eAKOCWlmSbkuWG3qwZoIoZAA" +
			"GOMXHwLa6XgHV6QSKuIFrw2ELX/fCwZ8QiDgdeGXIQjvWlY4YzP8k+PvvRg2CURVAkOdVeZdYPWax8j0FVWfM0IL40" +
			"hrlyzQC7ITShVSxMdg2nTLTlsFRspNckzqN5hBZ+0tlO5spbiTaDwIDAQAB" + "\r";
	static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/" + "\r"
			+ "fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" + "\r"
			+ "imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" + "\r"
			+ "ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" + "\r"
			+ "WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" + "\r"
			+ "kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" + "\r"
			+ "XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" + "\r"
			+ "n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" + "\r"
			+ "7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" + "\r"
			+ "7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" + "\r"
			+ "L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" + "\r"
			+ "JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" + "\r"
			+ "MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" + "\r" + "c7o0HLlMsYPAzJw="
			+ "\r";

	public static void main(String[] args) throws Exception
	{
		String source = "zxp";
		InputStream publicIS = new FileInputStream("C:\\rsa_public_key.pem");
		InputStream privateIS = new FileInputStream("C:\\pkcs8_rsa_private_key.pem");
		// PublicKey publicKey = RSAUtils.loadPublicKey(PUCLIC_KEY);
		PublicKey publicKey = RSAUtils.loadPublicKey(publicIS);
		// PrivateKey privateKey = RSAUtils.loadPrivateKey(PRIVATE_KEY);
		PrivateKey privateKey = RSAUtils.loadPrivateKey(privateIS);
		byte[] b1 = RSAUtils.encryptData(source.getBytes(), publicKey);
		System.out.println(">>>" + new String(RSAUtils.decryptData(b1, privateKey)));
	}
}
