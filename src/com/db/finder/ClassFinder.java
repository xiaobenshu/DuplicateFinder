package com.db.finder;

import java.util.ArrayList;
import java.util.List;

import com.db.finder.FileUtil.Log;

public class ClassFinder {

	List<Token> mTokenList;
	String mContextString;
	
	public ClassFinder(String content) {
		// TODO Auto-generated constructor stub
		mTokenList =  new ArrayList<>();
		mContextString = content;
	}

	
	
	
	public void beginParser(){
		
		if (true) {
			return ;
		}
		
		
		int index = 0;
		String tempToken="";
		
		
		char lastChar =' ';
		char currentChar=' ';
		
		
		while (index < mContextString.length() ){
			currentChar = mContextString.charAt(index);	
			
			//过滤源码中的 注释     形如 /*note */
			if (currentChar == '*' && lastChar == '/') {				
				while (index < mContextString.length()) {
					currentChar = mContextString.charAt(index);
					if (currentChar == '/' && lastChar == '*') {
						break;
					}
					index++;
					lastChar = currentChar;
				}
			}
			//过滤源码中 的注释  形如//
			if (currentChar == '/' && lastChar == '/') {				
				while (index < mContextString.length()) {
					currentChar = mContextString.charAt(index);
					if (currentChar == '\n') {
						break;
					}
					index++;
					lastChar = currentChar;
				}
			}

			if (mContextString.charAt(index) ==' ' 
				|| mContextString.charAt(index) =='\r' 
				|| mContextString.charAt(index) =='\n' 
				|| mContextString.charAt(index) =='\t'
				|| mContextString.charAt(index) =='*'
				|| mContextString.charAt(index) ==';'
				|| mContextString.charAt(index) =='\\'
				|| mContextString.charAt(index) =='/') {
				if (tempToken.length() >0) {
					mTokenList.add(new Token(tempToken));
				}
				tempToken="";
			}else{
				tempToken += mContextString.charAt(index);
			}
			
			
			index++;
			lastChar = currentChar;
		}
		
		for (int i = 0; i < mTokenList.size(); i++) {
			Log.i(mTokenList.get(i).mToken );
		}
		
	}
	
	
	private class Token{
		String mToken;
		public Token(String token) {
			// TODO Auto-generated constructor stub
			mToken = token;
		}
	}
}
