
package com.bside.breadgood.ddd.bakery.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BakeryPageController {

    @GetMapping("/pages/bakery/detail")
    public String bakeryDetailPage() {
        return "pages/bakeryDetail";
    }

    @GetMapping("/pages/bakery/list")
    public String bakeryListPage() {
        return "pages/bakeryList";
    }
}

