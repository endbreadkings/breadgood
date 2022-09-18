import 'package:breadgood_app/modules/dashboard/controller/dashboard_controller.dart';
import 'package:get/get.dart';

class InitBinding implements Bindings {
  @override
  void dependencies() {
    Get.put(DashboardController());
  }
}