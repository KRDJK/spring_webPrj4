package com.project.web_prj.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
// 페이지 정보를 담고 있는 클래스
public class Page {

    private int pageNum; // 페이지 번호
    private int amount; // 한 페이지 당 배치할 게시물 수


    // 기본 생성자
    public Page() {
        this.pageNum = 1;
        this.amount = 10;
    }


    // setter
    public void setPageNum(int pageNum) {
        if (pageNum <= 0 || pageNum > Integer.MAX_VALUE) { // int가 허용하는 범위 내에서만 작동하도록 막는 기능까지!!
            this.pageNum = 1;
            return;
        }
        this.pageNum = pageNum;
    }

    public void setAmount(int amount) {
        if (amount < 10 || amount > 100 || amount > Integer.MAX_VALUE) { // 얘는 왜 안될까
            this.amount = 10;
            return;
        }
        this.amount = amount;
    }

    
    // Maria DB  LIMIT 첫번째 숫자 구하기
    public int getStart() { // getBanana라고 하면 #{banana}를 사용할 수 있다!!
        return (pageNum -1) * amount;
    }
}