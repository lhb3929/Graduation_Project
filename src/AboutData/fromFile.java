package AboutData;
//
//fromFile
//Graduation_Project
//
//Created by daBiaoGe on 2018/4/25.
//Copyright © 2018年 刘宏斌. All rights reserved.
//

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fromFile {
	
	// 默认数据文件位置  。。。。 文件更换时需修改的变量
	static final String defaultFilePath = "/Users/liuhongbin/Documents/Documents/毕业论文/Data/hex.hex";
    // 代码读取文件的实际地址
	String filePath ;
	
  
  
	public fromFile(String item) {
	    this.filePath = item;
	}
	public fromFile() {
	    this(defaultFilePath);
	}
	public List<String> ReadFile() throws IOException  {
		List<String> strings  = new ArrayList<String>();
		BufferedReader reader;
		String item;
		try {
			reader = new BufferedReader(new FileReader(new File(filePath)));
			try {
				while( (item = reader.readLine()) != null ) {
					strings.add(item);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return strings;
	}
	
  
}
