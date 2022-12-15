import 'package:get/get.dart';
import 'package:breadgood_app/modules/register_bakery_renewal/scene/register_bakery_renewal_scene.dart';

enum RouteName { Home, Add, My, More }

class DashboardController extends GetxService {
  static DashboardController get to => Get.find();

  RxInt currentIndex = 0.obs;

  void changePageIndex(int index) {
    if (index == 1) {
      Get.to(() => RegisterBakeryRenewalScene());
      return;
    }

    currentIndex(index);
  }
}
