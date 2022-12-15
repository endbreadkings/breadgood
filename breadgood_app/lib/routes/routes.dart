import 'package:breadgood_app/modules/dashboard/scenes/dashboard_scene.dart';
import 'package:breadgood_app/modules/login/scene/login_scene.dart';
import 'package:breadgood_app/modules/login/scene/login_webview.dart';
import 'package:breadgood_app/modules/main/scene/bread_store_deatil_webview.dart';
import 'package:breadgood_app/modules/main/scene/main_scene.dart';
import 'package:breadgood_app/modules/register_bakery/scene/celebrate_register_scene.dart';
import 'package:breadgood_app/modules/signup/controller/signup_controller.dart';
import 'package:breadgood_app/modules/signup/scene/bread_style_scene.dart';
import 'package:breadgood_app/modules/signup/scene/nick_name_scene.dart';
import 'package:breadgood_app/modules/my_page/ui/bread_style_gridview.dart';
import 'package:breadgood_app/modules/my_page/scene/my_bread_style_edit_scene.dart';
import 'package:breadgood_app/modules/my_page/scene/my_nickname_edit_scene.dart';
import 'package:breadgood_app/modules/my_page/scene/my_page_scene.dart';
import 'package:breadgood_app/modules/my_page/scene/my_info_edit_scene.dart';
import 'package:breadgood_app/modules/signup/scene/policy_scene.dart';
import 'package:breadgood_app/modules/signup/scene/terms_webview.dart';
import 'package:get/get.dart';
import 'package:breadgood_app/modules/register_bakery/scene/already_registered_bakery.dart';
import 'package:breadgood_app/modules/register_bakery/scene/search_bakery_scene.dart';
import 'package:breadgood_app/modules/register_bakery/scene/select_bakery_category_scene.dart';
import 'package:breadgood_app/modules/register_review/scene/register_review_scene.dart';
import 'package:breadgood_app/modules/further_info/further_info.dart';
import 'package:breadgood_app/storages/user_storage.dart';

Future isLoggedIn = UserStorage().getLoggedIn();

initRoute() {
  isLoggedIn.then((res) {
    if (res == "true") {
      return Get.offAndToNamed('/dashboard');
    } else {
      return Get.offAndToNamed('/');
    }
  }).catchError(
    (e) {
      return Get.offAndToNamed('/');
    },
  );
}

final routes = [
  GetPage(name: '/', page: () => LoginScene()),
  GetPage(name: '/dashboard', page: () => DashboardScene()),
  GetPage(
    name: '/main',
    page: () => MainScene(),
  ),
  GetPage(name: '/login/oauth2/confirm', page: () => LoginWebView()),
  GetPage(
      name: '/signup/policy',
      page: () => AgreePolicyScene(),
      binding: BindingsBuilder(() {
        Get.put(SignUpController());
      })),
  GetPage(name: '/signup/policy/details', page: () => TermsWebView()),
  GetPage(
    name: '/signup/nickname',
    page: () => NickNameScene(),
  ),
  GetPage(name: '/signup/breadstyle', page: () => BreadStyleScene()),
  GetPage(name: '/details', page: () => BreadStoreDetailWebView()),
  GetPage(name: '/my_page/mypage', page: () => MyPageScene()),
  GetPage(name: '/my_page/my_info_edit', page: () => MyInfoEditScene()),
  GetPage(name: '/my_page/my_nickname_edit', page: () => MyNickNameEditScene()),
  GetPage(name: '/my_page/bread_style_page', page: () => BreadStyleGridView(0)),
  GetPage(
      name: '/my_page/my_breadstyle_edit_page',
      page: () => MyBreadStyleEditScene()),
  GetPage(
      name: '/register_review/register_reivew_page',
      page: () => RegisterReviewScene()),
  GetPage(
      name: '/register_bakery/search_bakery_page',
      page: () => SearchBakeryScene()),
  GetPage(name: '/further_info/further_info', page: () => FurtherInfo()),
  GetPage(
      name: '/register_bakery/select_bakery_category',
      page: () => SelectBakeryCategoryScene()),
  GetPage(
      name: '/register_bakery/celebrate_register_page',
      page: () => CelebrateRegisterScene()),
  GetPage(
      name: '/register_bakery/already_registered_bakery',
      page: () => AlreadyRegisteredBakeryScene()),
];
