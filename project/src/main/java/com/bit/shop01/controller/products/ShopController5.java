package com.bit.shop01.controller.products;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.shop01.bbs.AttachFile;
import com.bit.shop01.page.PagingHelper;
import com.bit.shop01.page.WebContants;
import com.bit.shop01.products.ProductsService;
import com.bit.shop01.products.ProductsVo;

@Controller
public class ShopController5 {

	 @Autowired
	    private ProductsService productsService;
	   
	    @RequestMapping(value="/sale", method={RequestMethod.GET, RequestMethod.POST})
	    public String sale(String procd,  Integer curPage,  Model model ,String searchWord) throws Exception{
	               
	        if (procd == null) procd = "sale";
	        if (curPage == null) curPage = 1;
	        if (searchWord == null) searchWord = "";
	        
	        int numPerPage = 10;// 페이지당 레코드 수 지정
	        int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	        
	        int totalRecord = productsService.getTotalRecord(procd, searchWord);
	        
	        PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	        productsService.setPagingHelper(pagingHelper);
	        
	        int start = pagingHelper.getStartRecord();
	        int end = pagingHelper.getEndRecord();

	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord,start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	        
	   
	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        model.addAttribute("curPage", curPage);//curPage는 null 값이면 1로 만들어야 하므로
	        
	       
	        return "products/saleDetail"; 
	    }
	    
	    
	    
	    
	    @RequestMapping(value="/sale_write", method=RequestMethod.GET)
	    public String sale_write(String procd, Model model) throws Exception {
	       
	        //게시판 이름
	        String pronm = productsService.getProductsNm(procd);
	        model.addAttribute("pronm", pronm);
	       
	        return "products/saleDetail_write";
	    }
	   
	    @RequestMapping(value="/sale_write", method=RequestMethod.POST)
	    public String sale_write(ProductsVo productsVo , MultipartHttpServletRequest mpRequest) throws Exception{
	        productsService.insert(productsVo);
	        productsVo.setProductNum(productsService.getNewProductsVo().getProductNum());
	        
	    
	        
	        //파일업로드
	        List<MultipartFile> fileList = mpRequest.getFiles("upload");
	        for(MultipartFile mf : fileList){
	        String filename = mf.getOriginalFilename();
	        mf.transferTo(new File(WebContants.BASE_PATH + filename));
	        }
	       
	        
	        //파일데이터 삽입
	        
	        int size = fileList.size();
	        for (int i = 0; i < size; i++) {
	         MultipartFile mpFile = fileList.get(i);
	         AttachFile attachFile = new AttachFile();
	         String filename = mpFile.getOriginalFilename();
	         attachFile.setFilename(filename);
	         attachFile.setFiletype(mpFile.getContentType());
	         attachFile.setFilesize(mpFile.getSize());
	         attachFile.setProductNum(productsVo.getProductNum());
	         productsService.insertAttachFile(attachFile);
	        }     
	        
	        
	        return "redirect:/sale/?procd=" + productsVo.getProcd();
	    }
	
	    
	    @RequestMapping(value="/sale_detail", method=RequestMethod.GET)
	    public String sale_detail(int productNum, Integer curPage, String procd, String searchWord, Model model){
	    	
	    	
	    	File dir = new File("C:\\gitK6\\project\\src\\main\\webapp\\resources\\imgs2"); 

			File[] fileList = dir.listFiles();

				System.out.println("start");
				for(int i = 0 ; i < fileList.length ; i++){

					File file = fileList[i]; 

					if(file.isFile()){

						System.out.println("\t 파일 이름 = " + file.getName());

					} else {
						System.out.println("파일없음");
					}

				}

			System.out.println("end");
	    	
	    	
	    	  int numPerPage = 10;// 페이지당 레코드 수 지정
	    	  int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	    	  if (searchWord == null) searchWord = ""; // 검색어가 null 이면 ""으로 변경 
	    	  
	    	  
	    	  //목록보기
	    	  int totalRecord = productsService.getTotalRecord(procd, searchWord);
	    	  System.out.println(curPage);
	    	  PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	    	  productsService.setPagingHelper(pagingHelper);
	    	  
	    	  int start = pagingHelper.getStartRecord();
	    	  int end = pagingHelper.getEndRecord();
	       
	       
	        //상세보기
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        ArrayList<AttachFile> attachFileList = productsService.getAttachFileList(productNum);
	 
	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord, start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	       
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        
	        
	        model.addAttribute("thisProductsVo", thisProductsVo);

	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        model.addAttribute("attachFileList", attachFileList);
	       
	        return "products/saleDetail_detail";
	    }
	    
	    
	    
	    @RequestMapping(value="/sale_delete", method=RequestMethod.GET)
	    public String sale_delete(int productNum,
	            String procd , Integer curPage, 
	            String searchWord) throws Exception {
	       
	        productsService.delete(productNum);
	       
	        return "redirect:/sale/?procd=" + procd  + 
	        		  "&searchWord=" + searchWord;
	 }  
	   
	    
	    @RequestMapping(value="/sale_edit", method=RequestMethod.GET)
	    public String sale_edit(int productNum,
	            String procd,
	            Model model) throws Exception {
	       
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        String pronm = productsService.getProductsNm(procd);
	         
	        //수정페이지에서 보일 게시글 정보
	        model.addAttribute("thisProductsVo", thisProductsVo);
	        model.addAttribute("pronm", pronm);
	       
	        return "products/saleDetail_edit";
	    }
	   
	    
	    
	    @RequestMapping(value="/sale_edit", method=RequestMethod.POST)
	    public String sale_edit(ProductsVo productsVo,  Integer curPage,
	            String procd, String searchWord,MultipartHttpServletRequest mpRequest, Model model) throws Exception {
	       
	       productsService.update(productsVo);
	       
	       //파일업로드
	       List<MultipartFile> fileList = mpRequest.getFiles("upload");
	       for(MultipartFile mf : fileList){
	       String filename = mf.getOriginalFilename();
	       mf.transferTo(new File(WebContants.BASE_PATH + filename));
	       }
	       
	       //파일데이터 삽입
	       int size = fileList.size();
	       for (int i = 0; i < size; i++) {
	        MultipartFile mpFile = fileList.get(i);
	        AttachFile attachFile = new AttachFile();
	        String filename = mpFile.getOriginalFilename();
	        attachFile.setFilename(filename);
	        attachFile.setFiletype(mpFile.getContentType());
	        attachFile.setFilesize(mpFile.getSize());
	        attachFile.setProductNum(productsVo.getProductNum());
	        productsService.insertAttachFile(attachFile);
	       }  
	       
	        return "redirect:/sale_detail/?productNum=" + productsVo.getProductNum() +
	                "&procd=" + productsVo.getProcd() +  "&curPage=" + curPage  +
	                "&searchWord=" + searchWord;
	    }
	    

		// 로그인 후


	    @RequestMapping(value="/sale2", method={RequestMethod.GET, RequestMethod.POST})
	    public String sale2(String procd,  Integer curPage,  Model model ,String searchWord) throws Exception{
	               
	        if (procd == null) procd = "sale";
	        if (curPage == null) curPage = 1;
	        if (searchWord == null) searchWord = "";
	        
	        int numPerPage = 10;// 페이지당 레코드 수 지정
	        int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	        
	        int totalRecord = productsService.getTotalRecord(procd, searchWord);
	        
	        PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	        productsService.setPagingHelper(pagingHelper);
	        
	        int start = pagingHelper.getStartRecord();
	        int end = pagingHelper.getEndRecord();

	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord,start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	        
	   
	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        model.addAttribute("curPage", curPage);//curPage는 null 값이면 1로 만들어야 하므로
	        
	       
	        return "products2/saleDetail2"; 
	    }
	    
	    
	    
	    
	    @RequestMapping(value="/sale_write2", method=RequestMethod.GET)
	    public String sale_write2(String procd, Model model) throws Exception {
	       
	        //게시판 이름
	        String pronm = productsService.getProductsNm(procd);
	        model.addAttribute("pronm", pronm);
	       
	        return "products2/saleDetail_write2";
	    }
	   
	    @RequestMapping(value="/sale_write2", method=RequestMethod.POST)
	    public String sale_write2(ProductsVo productsVo , MultipartHttpServletRequest mpRequest) throws Exception{
	        productsService.insert(productsVo);
	        productsVo.setProductNum(productsService.getNewProductsVo().getProductNum());
	        
	    
	        
	        //파일업로드
	        List<MultipartFile> fileList = mpRequest.getFiles("upload");
	        for(MultipartFile mf : fileList){
	        String filename = mf.getOriginalFilename();
	        mf.transferTo(new File(WebContants.BASE_PATH + filename));
	        }
	       
	        
	        //파일데이터 삽입
	        
	        int size = fileList.size();
	        for (int i = 0; i < size; i++) {
	         MultipartFile mpFile = fileList.get(i);
	         AttachFile attachFile = new AttachFile();
	         String filename = mpFile.getOriginalFilename();
	         attachFile.setFilename(filename);
	         attachFile.setFiletype(mpFile.getContentType());
	         attachFile.setFilesize(mpFile.getSize());
	         attachFile.setProductNum(productsVo.getProductNum());
	         productsService.insertAttachFile(attachFile);
	        }     
	        
	        
	        return "redirect:/sale2/?procd=" + productsVo.getProcd();
	    }
	
	    
	    @RequestMapping(value="/sale_detail2", method=RequestMethod.GET)
	    public String sale_detail2(int productNum, Integer curPage, String procd, String searchWord, Model model){
	    	
	    	
	    	File dir = new File("C:\\gitK6\\project\\src\\main\\webapp\\resources\\imgs2"); 

			File[] fileList = dir.listFiles();

				System.out.println("start");
				for(int i = 0 ; i < fileList.length ; i++){

					File file = fileList[i]; 

					if(file.isFile()){

						System.out.println("\t 파일 이름 = " + file.getName());

					} else {
						System.out.println("파일없음");
					}

				}

			System.out.println("end");
	    	
	    	
	    	  int numPerPage = 10;// 페이지당 레코드 수 지정
	    	  int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	    	  if (searchWord == null) searchWord = ""; // 검색어가 null 이면 ""으로 변경 
	    	  
	    	  
	    	  //목록보기
	    	  int totalRecord = productsService.getTotalRecord(procd, searchWord);
	    	  System.out.println(curPage);
	    	  PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	    	  productsService.setPagingHelper(pagingHelper);
	    	  
	    	  int start = pagingHelper.getStartRecord();
	    	  int end = pagingHelper.getEndRecord();
	       
	       
	        //상세보기
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        ArrayList<AttachFile> attachFileList = productsService.getAttachFileList(productNum);
	 
	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord, start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	       
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        
	        
	        model.addAttribute("thisProductsVo", thisProductsVo);

	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        model.addAttribute("attachFileList", attachFileList);
	       
	        return "products2/saleDetail_detail2";
	    }
	    
	    
	    
	    @RequestMapping(value="/sale_delete2", method=RequestMethod.GET)
	    public String sale_delete2(int productNum,
	            String procd , Integer curPage, 
	            String searchWord) throws Exception {
	       
	        productsService.delete(productNum);
	       
	        return "redirect:/sale2/?procd=" + procd  + 
	        		  "&searchWord=" + searchWord;
	 }  
	   
	    
	    @RequestMapping(value="/sale_edit2", method=RequestMethod.GET)
	    public String sale_edit2(int productNum,
	            String procd,
	            Model model) throws Exception {
	       
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        String pronm = productsService.getProductsNm(procd);
	         
	        //수정페이지에서 보일 게시글 정보
	        model.addAttribute("thisProductsVo", thisProductsVo);
	        model.addAttribute("pronm", pronm);
	       
	        return "products2/saleDetail_edit2";
	    }
	   
	    
	    
	    @RequestMapping(value="/sale_edit2", method=RequestMethod.POST)
	    public String sale_edit2(ProductsVo productsVo,  Integer curPage,
	            String procd, String searchWord,MultipartHttpServletRequest mpRequest, Model model) throws Exception {
	       
	       productsService.update(productsVo);
	       
	       //파일업로드
	       List<MultipartFile> fileList = mpRequest.getFiles("upload");
	       for(MultipartFile mf : fileList){
	       String filename = mf.getOriginalFilename();
	       mf.transferTo(new File(WebContants.BASE_PATH + filename));
	       }
	       
	       //파일데이터 삽입
	       int size = fileList.size();
	       for (int i = 0; i < size; i++) {
	        MultipartFile mpFile = fileList.get(i);
	        AttachFile attachFile = new AttachFile();
	        String filename = mpFile.getOriginalFilename();
	        attachFile.setFilename(filename);
	        attachFile.setFiletype(mpFile.getContentType());
	        attachFile.setFilesize(mpFile.getSize());
	        attachFile.setProductNum(productsVo.getProductNum());
	        productsService.insertAttachFile(attachFile);
	       }  
	       
	        return "redirect:/sale_detail2/?productNum=" + productsVo.getProductNum() +
	                "&procd=" + productsVo.getProcd() +  "&curPage=" + curPage  +
	                "&searchWord=" + searchWord;
	    }
	    
	    
	    
	    // 관리자 페이지
	    
	    
	    @RequestMapping(value="/sale3", method={RequestMethod.GET, RequestMethod.POST})
	    public String sale3(String procd,  Integer curPage,  Model model ,String searchWord) throws Exception{
	               
	        if (procd == null) procd = "sale";
	        if (curPage == null) curPage = 1;
	        if (searchWord == null) searchWord = "";
	        
	        int numPerPage = 10;// 페이지당 레코드 수 지정
	        int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	        
	        int totalRecord = productsService.getTotalRecord(procd, searchWord);
	        
	        PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	        productsService.setPagingHelper(pagingHelper);
	        
	        int start = pagingHelper.getStartRecord();
	        int end = pagingHelper.getEndRecord();

	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord,start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	        
	   
	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        model.addAttribute("curPage", curPage);//curPage는 null 값이면 1로 만들어야 하므로
	        
	       
	        return "products3/saleDetail3"; 
	    }
	    
	    
	    
	    
	    @RequestMapping(value="/sale_write3", method=RequestMethod.GET)
	    public String sale_write3(String procd, Model model) throws Exception {
	       
	        //게시판 이름
	        String pronm = productsService.getProductsNm(procd);
	        model.addAttribute("pronm", pronm);
	       
	        return "products3/saleDetail_write3";
	    }
	   
	    @RequestMapping(value="/sale_write3", method=RequestMethod.POST)
	    public String sale_write3(ProductsVo productsVo , MultipartHttpServletRequest mpRequest) throws Exception{
	        productsService.insert(productsVo);
	        productsVo.setProductNum(productsService.getNewProductsVo().getProductNum());
	        
	    
	        
	        //파일업로드
	        List<MultipartFile> fileList = mpRequest.getFiles("upload");
	        for(MultipartFile mf : fileList){
	        String filename = mf.getOriginalFilename();
	        mf.transferTo(new File(WebContants.BASE_PATH + filename));
	        }
	       
	        
	        //파일데이터 삽입
	        
	        int size = fileList.size();
	        for (int i = 0; i < size; i++) {
	         MultipartFile mpFile = fileList.get(i);
	         AttachFile attachFile = new AttachFile();
	         String filename = mpFile.getOriginalFilename();
	         attachFile.setFilename(filename);
	         attachFile.setFiletype(mpFile.getContentType());
	         attachFile.setFilesize(mpFile.getSize());
	         attachFile.setProductNum(productsVo.getProductNum());
	         productsService.insertAttachFile(attachFile);
	        }     
	        
	        
	        return "redirect:/sale3/?procd=" + productsVo.getProcd();
	    }
	
	    
	    @RequestMapping(value="/sale_detail3", method=RequestMethod.GET)
	    public String sale_detail3(int productNum, Integer curPage, String procd, String searchWord, Model model){
	    	
	    	
	    	File dir = new File("C:\\gitK6\\project\\src\\main\\webapp\\resources\\imgs2"); 

			File[] fileList = dir.listFiles();

				System.out.println("start");
				for(int i = 0 ; i < fileList.length ; i++){

					File file = fileList[i]; 

					if(file.isFile()){

						System.out.println("\t 파일 이름 = " + file.getName());

					} else {
						System.out.println("파일없음");
					}

				}

			System.out.println("end");
	    	
	    	
	    	  int numPerPage = 10;// 페이지당 레코드 수 지정
	    	  int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
	    	  if (searchWord == null) searchWord = ""; // 검색어가 null 이면 ""으로 변경 
	    	  
	    	  
	    	  //목록보기
	    	  int totalRecord = productsService.getTotalRecord(procd, searchWord);
	    	  System.out.println(curPage);
	    	  PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);  
	    	  productsService.setPagingHelper(pagingHelper);
	    	  
	    	  int start = pagingHelper.getStartRecord();
	    	  int end = pagingHelper.getEndRecord();
	       
	       
	        //상세보기
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        ArrayList<AttachFile> attachFileList = productsService.getAttachFileList(productNum);
	 
	        ArrayList<ProductsVo> list = productsService.getProductsList(procd, searchWord, start, end);
	        String pronm = productsService.getProductsNm(procd);
	        Integer no = productsService.getListNo();
	        Integer prevLink = productsService.getPrevLink();
	        Integer nextLink = productsService.getNextLink();
	        Integer firstPage = productsService.getFirstPage();
	        Integer lastPage = productsService.getLastPage();
	        int[] pageLinks = productsService.getPageLinks();
	       
	        
	        model.addAttribute("no", no);
	        model.addAttribute("prevLink", prevLink);
	        model.addAttribute("nextLink", nextLink);
	        model.addAttribute("firstPage", firstPage);
	        model.addAttribute("lastPage", lastPage);
	        model.addAttribute("pageLinks", pageLinks);
	        
	        
	        model.addAttribute("thisProductsVo", thisProductsVo);

	        model.addAttribute("list", list);
	        model.addAttribute("pronm", pronm);
	        model.addAttribute("procd", procd);
	        model.addAttribute("attachFileList", attachFileList);
	       
	        return "products3/saleDetail_detail3";
	    }
	    
	    
	    
	    @RequestMapping(value="/sale_delete3", method=RequestMethod.GET)
	    public String sale_delete3(int productNum,
	            String procd , Integer curPage, 
	            String searchWord) throws Exception {
	       
	        productsService.delete(productNum);
	       
	        return "redirect:/sale3/?procd=" + procd  + 
	        		  "&searchWord=" + searchWord;
	 }  
	   
	    
	    @RequestMapping(value="/sale_edit3", method=RequestMethod.GET)
	    public String sale_edit3(int productNum,
	            String procd,
	            Model model) throws Exception {
	       
	        ProductsVo thisProductsVo = productsService.getProductVo(productNum);
	        String pronm = productsService.getProductsNm(procd);
	         
	        //수정페이지에서 보일 게시글 정보
	        model.addAttribute("thisProductsVo", thisProductsVo);
	        model.addAttribute("pronm", pronm);
	       
	        return "products3/saleDetail_edit3";
	    }
	   
	    
	    
	    @RequestMapping(value="/sale_edit3", method=RequestMethod.POST)
	    public String sale_edit3(ProductsVo productsVo,  Integer curPage,
	            String procd, String searchWord,MultipartHttpServletRequest mpRequest, Model model) throws Exception {
	       
	       productsService.update(productsVo);
	       
	       //파일업로드
	       List<MultipartFile> fileList = mpRequest.getFiles("upload");
	       for(MultipartFile mf : fileList){
	       String filename = mf.getOriginalFilename();
	       mf.transferTo(new File(WebContants.BASE_PATH + filename));
	       }
	       
	       //파일데이터 삽입
	       int size = fileList.size();
	       for (int i = 0; i < size; i++) {
	        MultipartFile mpFile = fileList.get(i);
	        AttachFile attachFile = new AttachFile();
	        String filename = mpFile.getOriginalFilename();
	        attachFile.setFilename(filename);
	        attachFile.setFiletype(mpFile.getContentType());
	        attachFile.setFilesize(mpFile.getSize());
	        attachFile.setProductNum(productsVo.getProductNum());
	        productsService.insertAttachFile(attachFile);
	       }  
	       
	        return "redirect:/sale_detail3/?productNum=" + productsVo.getProductNum() +
	                "&procd=" + productsVo.getProcd() +  "&curPage=" + curPage  +
	                "&searchWord=" + searchWord;
	    }
	    
	    
		

	}


	

