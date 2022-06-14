import 'package:get/get.dart';

enum RouteName {Home, Add, My, More}

class DashboardController extends GetxService {
  static DashboardController get to => Get.find();
  RxInt currentIndex = 0.obs;

  void changePageIndex(int index) {
    currentIndex(index);
  }
}