import 'package:breadgood_app/modules/register_bakery_renewal/register_bakery_renewal.dart';
import 'package:get/get.dart';

enum RouteName {Home, Add, My, More}

class DashboardController extends GetxService {
  static DashboardController get to => Get.find();
  RxInt currentIndex = 0.obs;

  void changePageIndex(int index) {
    if (index == 1) {
      Get.to(() => RegisterBakeryRenewal());
      return;
    }

    currentIndex(index);
  }
}