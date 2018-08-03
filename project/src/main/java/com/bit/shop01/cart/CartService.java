package com.bit.shop01.cart;

import java.util.HashMap;
import java.util.List;

import com.bit.shop01.cart.model.entity.CartVo;

public interface CartService {
	
//	public int test();
	
	//장바구니에 추가
	public int insert(CartVo vo);

	//장바구 상품 확인
	public int countCart(HashMap<String, Object> params);
//	public int countCart(int productNum, String memId);

	//장바구니 수정
	public int editCart(CartVo vo);

	//장바구니 수량 변경
	public void updateCart(CartVo vo);
	
	//장바구니 목록
	public List<CartVo> listCart(String memId);

	//장바구니 금액 합계
	public int sumPrice(String memId);

	//장바구니 삭제
	public void delete(int cartNum);

	
}
