package com.lithium.esb.compliance.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.tukaani.xz.XZInputStream;

import com.lithium.streams.compliance.model.Payload;
import com.lithium.streams.compliance.model.SecureEvent;

public class XZDecompressFile {

	private XZInputStream xzInputStream;

	public SecureEvent decompressHdfsFile(SecureEvent secureEvent) {
		InputStream inputStream = new ByteArrayInputStream(secureEvent.getMessage());
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		try {
			xzInputStream = new XZInputStream(inputStream);
			byte[] bytes = new byte[1024];
			int readBytes = -1;
			while ((readBytes = xzInputStream.read(bytes)) > 1) {
				byteArrayOutputStream.write(bytes, 0, readBytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if (xzInputStream != null)
				try {
					xzInputStream.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		return new Payload(byteArrayOutputStream.toByteArray());
	}
}
