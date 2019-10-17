package Model;

/**
 * String을 받아서 코드를 생성해 주는 클래스
 * 알고리즘은 해쉬코드의 변형형을 사용한다. String 길이에 따라 변형방식이 달라진다.
 * 직급코드, 상품번호, 고객번호, 직원번호, 주문번호, 판매번호 등에 이용할 수 있음
 */
public class Encoder {
	
	int length;
	String code;
	
	public Encoder()
	{
	
	}
	public Encoder(String originalString)
	{
		length = originalString.length();
	}

	/**
	 * original String을 인코딩하여 그 결과를 code String에 넣는 함수
	 * 		<br>- 변환방식 : modula 2, modula 3, modula 5, 를 차례로 수행한 다음
	 * 		짝수값으로 모듈러를 수행한 경우는 나머지가 큰값부터, 홀수값으로 수행한 경우는 나머지가 작은값부터
	 * 		순서를 바꿔 배열한 후 해쉬코드로 바꾼다.
	 */
	public void encode(String originalString)
	{
		String buf[] = new String[5];	//나머지가 0, 1, 2, 3, 4 일때의 버퍼
		String prevString = "";	//정렬 전의 String
		String nextString = "";	//정렬이 완료된 String
		int index = 0;
		
		//Buffer 초기화
		clearBuffer(buf);
		
		prevString = originalString;
		
		for(int i=2; i<6; i++)	//modula 기준값이 바뀌는Loop (2, 3 4, 5)
		{
			if(i == 4) continue;	//4일 때는 루프 수행 안 함
			for(int j=0; j<prevString.length(); j++) //modula 연산 Loop
			{
				char c = prevString.charAt(j);
				buf[c % i] += c + "";
			}
			for(int j=0; j<i; j++)
			{
				if(i % 2 == 1)	//홀수이면
					nextString += buf[j];	//순차정렬
				else	//짝수이면
					nextString += buf[i-j-1];	//역차정렬
			}
			clearBuffer(buf);
			prevString = nextString;
			nextString = "";
		}
		code = String.valueOf(prevString.hashCode());		
	}
	
	/**
	 * original String을 넣으면 original String과 같은 길이의 코드를 리턴해 준다.
	 * @param originalString
	 * @return
	 */
	public String getCode(String originalString)
	{
		System.out.println("original string = " + originalString);
		encode(originalString);
		String tmpcode = code;
		for(int i=0; i<code.length(); i++)	//중간에 '-' 가 있으면 0으로 고친다.
		{
			if(code.charAt(i) == '-')
			{
				if(i == 0)
					tmpcode = "0" + code.substring(1, code.length());
				else
					tmpcode = code.substring(0, code.charAt(i-1)) + "0" + code.substring(0, code.length());
			}
		}
		code = tmpcode;
		
		if(code.length() > originalString.length()) code = code.substring(0, originalString.length());
		if(code.length() < originalString.length()) code += code.substring(0, originalString.length() - code.length());
		System.out.println("return code = " + code);
		return code;
	}
	
	public void clearBuffer(String[] buf)
	{
		for(int i=0; i<buf.length; i++)
		{
			buf[i] = "";
		}
	}
}