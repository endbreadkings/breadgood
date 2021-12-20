import 'package:breadgood_app/modules/login/screens/login_page.dart';
import 'package:breadgood_app/modules/login/screens/login_webview.dart';
import 'package:breadgood_app/modules/main/controller/main_map_controller.dart';
import 'package:breadgood_app/modules/main/screens/bakery_list_webview.dart';
import 'package:breadgood_app/modules/main/screens/bread_store_deatil_webview.dart';
import 'package:breadgood_app/modules/main/screens/main_list.dart';
import 'package:breadgood_app/modules/main/screens/main_map.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/modules/signup/screens/bread_style.dart';
import 'package:breadgood_app/modules/signup/screens/nick_name.dart';
import 'package:breadgood_app/modules/my_page/bread_style_gridview.dart';
import 'package:breadgood_app/modules/my_page/screens/my_bread_style_edit.dart';
import 'package:breadgood_app/modules/my_page/screens/my_nickname_edit.dart';
import 'package:breadgood_app/modules/my_page/screens/my_page.dart';
import 'package:breadgood_app/modules/my_page/screens/my_info_edit.dart';
import 'package:breadgood_app/modules/signup/screens/policy.dart';
import 'package:breadgood_app/modules/signup/screens/terms_webview.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/register_bakery/naver_search.dart';
import 'package:breadgood_app/modules/register_bakery/screens/search_bakery.dart';
import 'package:breadgood_app/modules/register_review/register_review.dart';

// 아래와 같은 양식으로 라우트 설정
// GetPage(name:"/first",page:()=>FirstNamedPage(),transition:Transition.zoom),

final Routes = [
  GetPage(name: '/', page: () => LoginPage()),
  GetPage(
    name: '/main',
    page: () => MainList(),
  ),
  GetPage(name: '/login/oauth2/confirm', page: () => LoginWebView()),
  GetPage(
      name: '/signup/policy',
      page: () => AgreePolicy(),
      binding: BindingsBuilder(() {
        Get.put(SignUpController());
      })),
  GetPage(name: '/signup/policy/details', page: () => TermsWebView()),
  GetPage(
    name: '/signup/nickname',
    page: () => NickName(),
    // binding: BindingsBuilder(() {
    //   Get.put(SignUpController());
    // })
  ),
  GetPage(name: '/signup/breadstyle', page: () => BreadStyle()),
  GetPage(name: '/details', page: () => BreadStoreDetailWebView()),
  GetPage(name: '/my_page/mypage', page: () => MyPage()),
  GetPage(name: '/my_page/my_info_edit', page: () => MyInfoEditPage()),
  GetPage(name: '/my_page/my_nickname_edit', page: () => MyNicknameEditPage()),
  GetPage(name: '/my_page/bread_style_page', page: () => BreadStylePage(0)
      // page:()=>BreadStylePage()
      ),
  GetPage(
      name: '/my_page/my_breadstyle_edit_page',
      page: () => MyBreadStyleEditPage()),
  GetPage(
      name: '/register_review/register_reivew_page',
      page: () => RegisterReviewPage()),
  GetPage(
      name: '/register_bakery/search_bakery_page',
      page: () => SearchBakeryPage()),
  GetPage(
    name: '/four',
  ),
  GetPage(
    name: '/five/:param',
  ),
];
