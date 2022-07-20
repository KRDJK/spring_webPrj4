package com.project.web_prj.common.search;

import com.project.web_prj.common.paging.Page;
import lombok.*;


@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Search {

    private Page page; // 검색 결과가 많으면 다 보여줄 수 없기 때문에 정보를 알고 있어야 한다.
    
    private String type; // 검색 조건
    
    private String keyword; // 검색 키워드


}
