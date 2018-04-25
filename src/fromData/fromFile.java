package fromData;
//
//fromFile
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class fromFile {
	
	// 默认数据文件位置  。。。。 文件更换时需修改的变量
	static final String defaultFilePath = "/Users/liuhongbin/Documents/Documents/毕业论文/Data/hex.hex";
    // 代码读取文件的实际地址
	String filePath ;
	List<String> strings ;
  
  
	public fromFile(String item) {
	    this.filePath = item;
	    strings = new ArrayList<String>();
	}
	public fromFile() {
	    this(defaultFilePath);
	}
	public void ReadFile() throws FileNotFoundException {
		OutputStream outputStream = new FileOutputStream(new File(filePath));
		
	}
	
  
}
