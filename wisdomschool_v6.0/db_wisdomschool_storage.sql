/*
 Navicat Premium Data Transfer

 Source Server         : lzt
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : db_wisdomschool_storage

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 17/04/2022 00:57:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `file_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_size` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_parent_folder` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_creation_date` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_creator` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_path` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `file_index`(`file_id`, `file_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('00a35c07-abf1-4440-bd23-41a9361cfd24', 'efgh.png', '0', 'thumbnail', '2022年01月06日', '20080041', 'file_0321a236d3ae4298acddcd6c009f902a.png');
INSERT INTO `file` VALUES ('06591baf-944f-45ae-b15f-d9ee20270d3c', 'ldrq.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_c333141ed1e9407cb7ab29428ecfce92.png');
INSERT INTO `file` VALUES ('25b0b82a-9d80-4d03-9dc2-38ff03553cb6', 'gnnd.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_0799bf6f9bf24133844ab03272ec0242.png');
INSERT INTO `file` VALUES ('2666da47-eb6f-4107-bebd-92837e1497b8', 'lrvc.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_5b0bc6cad6aa4056b9b45309ac5bb037.png');
INSERT INTO `file` VALUES ('26821031-dd4e-4a67-a078-b51bfcf4c0c8', 'jbec.png', '0', 'thumbnail', '2022年04月03日', '20080041', 'file_72dde17c183640c598f4e462cf1561c6.png');
INSERT INTO `file` VALUES ('2827dc74-d305-4129-b441-b7dab1da9bbf', 'otxr.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_0bdc724615944772afa8858be3566d54.png');
INSERT INTO `file` VALUES ('2b1a06ee-9bdd-495a-9e89-8e44fbba14c9', 'nkbm.jpg', '0', 'thumbnail', '2022年03月29日', '20080041', 'file_f5e9a0ec297f4c939ca75fe3b5d493f1.jpg');
INSERT INTO `file` VALUES ('2e73add1-92d1-4b57-ad12-b5fbcb796b8b', 'emwm.png', '0', 'thumbnail', '2022年01月06日', '202035020110', 'file_cae012aede3946ca8468f32ba9d546eb.png');
INSERT INTO `file` VALUES ('2fe272fb-78d8-4dba-a842-0311ecd0a184', 'fxha.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_d0c6c1402fb2446c955b88de4467d526.png');
INSERT INTO `file` VALUES ('32ee6caf-aee0-4740-8cf9-5371af9a9dd4', 'nivc.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_73831909b3ba47669a08f4913c7cefa4.png');
INSERT INTO `file` VALUES ('3324688f-ea48-40dd-8a7b-98acf09d0b96', 'mnxl.png', '0', 'thumbnail', '2022年03月14日', '202035020110', 'file_e03d9c3c1d5e45a2849b2426276431e7.png');
INSERT INTO `file` VALUES ('389c51e2-a365-4230-ada3-b4b310f54bf4', 'dpcq.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_9310b3d916fe4752abda95ee21a9d9d4.png');
INSERT INTO `file` VALUES ('389e490a-086e-48e9-a8dd-d1236d022f3e', 'ifrw.png', '0', 'thumbnail', '2022年03月22日', '202035020116', 'file_d3e5f012bb044d67a0ad7fb581b0bcc1.png');
INSERT INTO `file` VALUES ('3cbd5d58-1c79-4661-a595-e625c91e5585', 'rdih.png', '0', 'thumbnail', '2022年01月30日', 'schooladmin', 'file_c51141f55d324a97b1ca05d731285996.png');
INSERT INTO `file` VALUES ('43315cbc-feb8-49fc-92be-74fa13297c9a', 'bvtt.png', '0', 'thumbnail', '2022年03月29日', '20080041', 'file_0af5fee169134f0dade2ff14e5c87d48.png');
INSERT INTO `file` VALUES ('475abff6-fa18-4deb-b321-52363e624c4f', 'pdgl.jpg', '0', 'thumbnail', '2022年03月29日', '20080041', 'file_6100b64166394838be25e7113f5be69d.jpg');
INSERT INTO `file` VALUES ('4ea3d0d7-ff83-4a91-9969-90314535bba3', 'bgxb.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_3b2c1f942cb94e8494bb5c1557b6e345.png');
INSERT INTO `file` VALUES ('508fd051-54b4-4dab-bdf6-81fc85e33532', 'ilao.png', '0', 'thumbnail', '2022年01月05日', '202035020106', 'file_d087e392157f4c858fec4ce2c19f766b.png');
INSERT INTO `file` VALUES ('54a019b2-2143-4700-8c73-5707a55c4491', 'csdf.png', '0', 'thumbnail', '2022年03月29日', '20080041', 'file_5cce3c37a45f44918e1ed3956b944166.png');
INSERT INTO `file` VALUES ('555f13da-cd2f-4aea-9e52-4e5ee88d9fe4', 'cjmq.jpg', '0', 'thumbnail', '2022年04月04日', '20080041', 'file_65f9629260e5403fa1b567296ea5bcd6.jpg');
INSERT INTO `file` VALUES ('5b54f7ab-aaa3-4d46-9c1e-5e18d7742806', 'tscd.png', '0', 'thumbnail', '2022年01月05日', '202035020106', 'file_2e72eff1aace4560bcda6c9153f28955.png');
INSERT INTO `file` VALUES ('5dcb1d3f-0a0f-46ee-a60a-4e99d1d8a675', 'ofpe.png', '0', 'thumbnail', '2022年03月14日', '20080041', 'file_96c0f676a4874226a7f03d0aedd45b48.png');
INSERT INTO `file` VALUES ('64fb82d5-2dbb-4bcf-9917-20423c9505dd', 'xico.png', '0', 'thumbnail', '2022年03月18日', '20080041', 'file_ad18d339aca34ece9d421cd2cfec27b3.png');
INSERT INTO `file` VALUES ('67df38df-37d8-41ca-8336-f96bc997f99a', 'dwhf.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_5c97cc199c244b10b36e025bf63ac47f.png');
INSERT INTO `file` VALUES ('67fb2c09-2dd8-456b-a8e8-1db8d45c7a3b', 'lair.png', '0', 'thumbnail', '2022年04月04日', '20080041', 'file_bc0f350b98d84cb8bad1a2e38703db1f.png');
INSERT INTO `file` VALUES ('680ef518-219a-4c43-88e3-318ec260a55b', 'scad.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_e9215ad05345407c889102892b510272.png');
INSERT INTO `file` VALUES ('793f3562-b744-449d-ae85-1507d5ff021e', 'rcln.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_7668fb747716432db7055df069810f90.png');
INSERT INTO `file` VALUES ('7cc980a8-4e5b-4cb8-a9d1-66240313a8f8', 'wnwi.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_adba9323c0ac466eaff72706c2b248d8.png');
INSERT INTO `file` VALUES ('7e8ca4cb-e31b-4636-9370-ac6ae58f23aa', 'chfx.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_d9ce6be4c4954a69a537c3ca6042e23c.png');
INSERT INTO `file` VALUES ('8c256ccf-b4e7-43a5-bce3-8f89ed924578', 'uswp.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_f9fd4cf70f4149bf84de68cf32e97b71.png');
INSERT INTO `file` VALUES ('8e3b2f3c-115f-4faf-8d2d-d485c2a7c937', 'enkg.png', '0', 'thumbnail', '2022年01月06日', '20080041', 'file_020dc39570514283bc9838d5c1d5736c.png');
INSERT INTO `file` VALUES ('9461a5ae-0f86-48fd-bd7e-dd7254f3cf00', 'rgvf.png', '0', 'thumbnail', '2022年01月06日', '202035020110', 'file_20f81b4175354208847d24edc69a37c0.png');
INSERT INTO `file` VALUES ('9818cfa4-1f99-46bc-ab17-9cbd4f5d0f72', 'awxd.png', '0', 'thumbnail', '2022年04月16日', '202035020225', 'file_b0ac505e379144d2870f9e4ceaf894f9.png');
INSERT INTO `file` VALUES ('9a444f52-0805-4ef2-b49e-eefd6038ff18', 'ciww.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_7b1a2dbb844b4eda937172912669d834.png');
INSERT INTO `file` VALUES ('9f614228-8bc4-420d-9dc7-7f7e86aaec4c', 'kode.png', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_8cd71c4589bb4531bd77c655e44a2baf.png');
INSERT INTO `file` VALUES ('a97b1c20-2faf-439a-8cff-4933931e258b', 'dnaw.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_a5e65b84542d414494046d491b1c634d.png');
INSERT INTO `file` VALUES ('ae66d491-c634-4469-bec6-742406efa462', 'fqvw.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_a2b4365297454eae8ee3b30a0062a350.png');
INSERT INTO `file` VALUES ('b405e246-0ad8-4bf0-bcab-c72ee873a5ba', 'xogc.png', '0', 'thumbnail', '2022年03月29日', '20080041', 'file_1eab5b0dc8344fc99fe5978492df2d65.png');
INSERT INTO `file` VALUES ('b625aa8d-1ac8-488a-9f7f-91d469213291', 'ahiq.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_989d86c9634947e3a913de685a96a4cb.png');
INSERT INTO `file` VALUES ('b7df7761-8431-4392-8639-8db490d8d7de', 'bmlu.png', '0', 'thumbnail', '2022年01月05日', '202035020106', 'file_f224e9f04ee7475cb3b40e064eeba453.png');
INSERT INTO `file` VALUES ('ba843c90-ee85-45ce-9eaa-67dc6edbd7fe', 'lgdx.jpg', '0', 'thumbnail', '2022年03月18日', 'schooladmin', 'file_3b5f65032e824b1db2e1f3c102bc2b24.jpg');
INSERT INTO `file` VALUES ('bb553c2a-aac4-423a-b928-660b68c4b2f6', 'psmg.png', '0', 'thumbnail', '2022年04月03日', '20080041', 'file_775e1840b69c4184813974d09ffacf50.png');
INSERT INTO `file` VALUES ('c20dc5f8-302f-4138-ada4-d4423670e3fe', 'drjq.png', '0', 'thumbnail', '2022年04月16日', '202035020225', 'file_f17ee55ea66b47408a991254d3597c0f.png');
INSERT INTO `file` VALUES ('ccc5fa40-760b-4b80-ba28-d98d622af9bd', 'bjni.png', '0', 'thumbnail', '2022年01月05日', '202035020110', 'file_21e9a0304cd74aeca75be5142ddd2d5e.png');
INSERT INTO `file` VALUES ('d9f9f98b-36ba-4aaa-87a4-783b8aeb6581', 'iwnq.png', '0', 'thumbnail', '2022年03月29日', '202035020102', 'file_4b44ca32a3594a8c962411c6acc0a3ba.png');
INSERT INTO `file` VALUES ('ddc63e40-a888-43ad-902b-4ef8856e4e7c', 'ogtc.png', '0', 'thumbnail', '2022年03月22日', '202035020116', 'file_6b173f6732c84a3986584a44b78bb9b9.png');
INSERT INTO `file` VALUES ('deec7db5-727d-4896-8b66-05102ee5da01', 'rsqg.png', '0', 'thumbnail', '2022年01月06日', '202035020209', 'file_90232b095a374f9ea7fe3257f6c16920.png');
INSERT INTO `file` VALUES ('e9f62018-81be-4fe0-8708-5c13297de646', 'eucw.png', '0', 'thumbnail', '2022年01月06日', '202035020110', 'file_57beb8d1ad0d4d8e96d8b81aed72126b.png');
INSERT INTO `file` VALUES ('eb13e3d7-061e-40f2-864a-5a43ac159c15', 'qrrm.png', '0', 'thumbnail', '2022年04月03日', '20080041', 'file_c96971b4426f4c19b4a0b0931e8b2951.png');
INSERT INTO `file` VALUES ('f80dec11-51e0-40d6-940c-a8555e07f5c4', 'rmkh.png', '0', 'thumbnail', '2022年01月06日', '20080041', 'file_09f85c07ea164f15ae10a14c00466d37.png');
INSERT INTO `file` VALUES ('ff30e2a2-5d4c-4af7-a5f9-528467f6ee4d', 'smhx.png', '0', 'thumbnail', '2022年03月14日', '202035020110', 'file_cf4d61fed9d24262b53e1cf2166c6392.png');

-- ----------------------------
-- Table structure for folder
-- ----------------------------
DROP TABLE IF EXISTS `folder`;
CREATE TABLE `folder`  (
  `folder_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `folder_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `folder_creation_date` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `folder_creator` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `folder_parent` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `folder_constraint` int(0) NOT NULL,
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Records of folder
-- ----------------------------
INSERT INTO `folder` VALUES ('0153fbc5-4022-4274-8f62-c7395d3f992e', '第七章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('0435c1a7-6303-4d5a-aa0a-412703fc02fc', '语文_高技', '2021年02月20日', '100401306', '04c91161-38ad-4520-aa16-3ff4ea246790', 2);
INSERT INTO `folder` VALUES ('04c91161-38ad-4520-aa16-3ff4ea246790', '陈爱勤', '2020年02月28日', '100401306', 'root', 2);
INSERT INTO `folder` VALUES ('05f699ec-06f4-4232-adb3-6ab1d64c316e', '网店美工', '2021年04月09日', '100401213', 'root', 2);
INSERT INTO `folder` VALUES ('094c48c3-cab4-41c1-a0ab-3b019976452e', '测试', '2019年03月04日', 'admin', 'root', 2);
INSERT INTO `folder` VALUES ('0f741390-7eff-42ab-adf9-82a3d227ea5e', '技能训练', '2021年01月24日', '100401221', 'root', 2);
INSERT INTO `folder` VALUES ('12b11821-e63c-498f-84a3-44f40b6ea617', '上机', '2021年09月27日', '20080041', 'e29ea480-43a2-45eb-b83c-76706307c301', 2);
INSERT INTO `folder` VALUES ('1db6c544-0ab6-48c6-9ee5-6892d6f4ccad', 'PPT', '2021年10月09日', '20080041', 'e29ea480-43a2-45eb-b83c-76706307c301', 2);
INSERT INTO `folder` VALUES ('1f8152c1-c2a3-47d0-a5ea-7f8a49d7e2ed', '地方菜名实训', '2020年02月14日', '100402208', 'root', 2);
INSERT INTO `folder` VALUES ('265e5cac-1207-46c9-b1c9-7672ce78e8fc', '电机与变压器第五版', '2021年01月22日', '100401231', 'root', 2);
INSERT INTO `folder` VALUES ('2c2c190d-01cb-4c5b-9128-311763ac2949', '第十章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('32194047-a378-4910-88fc-30ba57ae4001', '李老师课件', '2019年03月18日', '1002', 'root', 2);
INSERT INTO `folder` VALUES ('38f58e77-ca7c-4fb9-b269-88a4c97bb311', '网站实践项目', '2021年01月22日', '100401219', 'root', 2);
INSERT INTO `folder` VALUES ('3f6b7cb9-8190-4446-941f-58ec0fdc4308', '计算机应用', '2021年01月15日', '100401209', 'root', 2);
INSERT INTO `folder` VALUES ('49d78a10-bc36-4bf5-a1b7-2c716c4950c2', 'excel', '2019年03月04日', 'admin', 'root', 2);
INSERT INTO `folder` VALUES ('4de59288-6548-4bc7-b2b1-4bf15b5283f2', '我的ppt文件mp3文件和文档', '2020年03月02日', '100401303', 'root', 2);
INSERT INTO `folder` VALUES ('54cb61f6-c46d-47bc-9655-b4c09eea0752', '电子技术', '2021年01月23日', '100401235', 'root', 2);
INSERT INTO `folder` VALUES ('55bb7abd-fe07-48da-9f2f-a20a6e0513be', '课件', '2019年03月29日', '1002', 'root', 2);
INSERT INTO `folder` VALUES ('5607339d-8e98-4202-9fe8-02a67d594a1c', '第二章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('654417f0-c272-471a-94a6-b40843230cdc', '第九章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('6b087dc3-eecc-4988-85de-c82609fc0bc5', '第六章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('6c568a95-8f5c-4da0-95c4-70896f40c1dc', '视频', '2019年03月18日', '1002', 'root', 2);
INSERT INTO `folder` VALUES ('6f961339-21ea-4f3e-b68c-c8e9821c3169', 'CDR视频', '2021年01月13日', '100401213', 'root', 2);
INSERT INTO `folder` VALUES ('730f1079-80d3-4866-9999-d219c4d4ca46', '语文_中技', '2020年02月28日', '100401306', '04c91161-38ad-4520-aa16-3ff4ea246790', 2);
INSERT INTO `folder` VALUES ('754343b7-6c64-4377-b82d-587b13c17d0c', '第八章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('7864fb81-9d73-4bad-a1f2-ba6d76e1e84e', 'FLASH', '2021年01月15日', '100401209', 'root', 2);
INSERT INTO `folder` VALUES ('7adb8468-1a67-4e9c-b563-e944188a45e6', '美术基础', '2021年01月23日', '100401229', 'root', 2);
INSERT INTO `folder` VALUES ('7f688bee-22e2-4c35-bf8c-f49a8bfc5d96', '第二版_第一册', '2020年02月28日', '100401306', '9eb82558-fa0e-4fe2-9625-e3b1700711e8', 2);
INSERT INTO `folder` VALUES ('802ecfb7-4716-4186-82ff-fc6e41613fb2', 'test12', '2021年09月13日', '2662114558', 'root', 2);
INSERT INTO `folder` VALUES ('82f695d1-7be2-462f-b878-3c37bed5b44a', '基于Proteus仿真的单片机技能应用', '2021年01月22日', '100401231', 'root', 2);
INSERT INTO `folder` VALUES ('834e5f1e-cd80-4495-90a9-a7b350ca2027', '日常文件', '2019年02月26日', 'admin', '08352c8d-69ac-4a71-a0ec-cf272da7bf21', 2);
INSERT INTO `folder` VALUES ('83a5f696-ff79-4b2b-a046-2d00325749c6', '网页设计', '2020年02月15日', '100401219', 'root', 2);
INSERT INTO `folder` VALUES ('858cc2f4-39dd-4eb2-a96c-16b4c79e7185', 'photoshop', '2021年04月09日', '100401209', 'root', 2);
INSERT INTO `folder` VALUES ('8614cabc-86ae-45c6-849a-4df4e2c25af0', '演示文档', '2020年02月14日', '100402207', 'root', 2);
INSERT INTO `folder` VALUES ('89c20f26-fa57-4174-8b23-8d36bcb0c725', '网店运营', '2021年01月13日', '100401213', 'root', 2);
INSERT INTO `folder` VALUES ('9967d4ac-976b-4205-a600-74e150bcefbe', 'CDR', '2021年01月13日', '100401213', 'root', 2);
INSERT INTO `folder` VALUES ('9eb82558-fa0e-4fe2-9625-e3b1700711e8', '德育', '2020年02月28日', '100401306', '04c91161-38ad-4520-aa16-3ff4ea246790', 2);
INSERT INTO `folder` VALUES ('b087e9d7-3e01-4726-a281-c1754b0fde7d', '第二版_第三册', '2020年02月28日', '100401306', '9eb82558-fa0e-4fe2-9625-e3b1700711e8', 2);
INSERT INTO `folder` VALUES ('b9765540-54f6-4150-a738-ab6a4d4097d9', '3DMAX项目设计', '2021年01月23日', '100401229', 'root', 2);
INSERT INTO `folder` VALUES ('bbf76f11-7c71-4b2b-9172-e0ca66bdb7a4', '第四章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('c4b36556-9ca0-4846-b348-abc0fb717aed', '电工基础第五版', '2021年01月22日', '100401231', 'root', 2);
INSERT INTO `folder` VALUES ('ca96a5d8-a4aa-4438-8e6c-bcbc615d4fe2', 'ppt', '2019年03月04日', 'admin', 'root', 2);
INSERT INTO `folder` VALUES ('cdb8aacd-796b-45a8-a376-e11879ea8205', '组装与维护', '2021年03月24日', '100401219', 'root', 2);
INSERT INTO `folder` VALUES ('cddebb2c-7cdc-48e6-9b24-37aafc341ace', '课后习题', '2019年10月12日', '20080040', 'e3be4ad3-dd1e-4839-bac9-666c093baae9', 2);
INSERT INTO `folder` VALUES ('ce3b4b18-f309-4169-a2b9-81c2a9c274b0', '我的视频', '2020年03月01日', '100401303', 'root', 2);
INSERT INTO `folder` VALUES ('cf64b34c-eeee-4c0f-a78d-1b64b6904fe7', '摄影摄像技术', '2021年01月23日', '100401229', 'root', 2);
INSERT INTO `folder` VALUES ('d3bad764-ed3c-4856-b7ea-87cb1992b11e', '第三章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('d5869932-ec5f-4b9b-9c61-14d734ce5e02', '第一章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('d6b58d1d-bb31-4c13-916b-a404768afb00', 'aaa', '2021年10月28日', '20080041', 'root', 2);
INSERT INTO `folder` VALUES ('deae5424-8468-43e4-add2-6c2787caa1b2', 'CorelDRAW', '2021年03月25日', '100401219', 'root', 2);
INSERT INTO `folder` VALUES ('def2df42-705d-4c42-b1d6-43dcea75d26e', '第五章', '2021年01月22日', '100401231', '265e5cac-1207-46c9-b1c9-7672ce78e8fc', 2);
INSERT INTO `folder` VALUES ('e29ea480-43a2-45eb-b83c-76706307c301', '面向对象程序设计', '2021年09月13日', '20080041', 'root', 2);
INSERT INTO `folder` VALUES ('e3384321-5e1b-44a5-bb6c-40c79f461d38', 'PS', '2021年04月09日', '100401213', 'root', 2);
INSERT INTO `folder` VALUES ('e3be4ad3-dd1e-4839-bac9-666c093baae9', '上机任务', '2019年09月23日', '20080040', 'root', 2);
INSERT INTO `folder` VALUES ('e3e5b7d3-b176-455e-8eef-ead6f6779e0f', '第二版_第二册', '2020年02月28日', '100401306', '9eb82558-fa0e-4fe2-9625-e3b1700711e8', 2);
INSERT INTO `folder` VALUES ('f362cfce-d2c6-422d-849a-81a3b773b9ab', '网页制作', '2021年03月24日', '100401219', 'root', 2);
INSERT INTO `folder` VALUES ('fdbc9c30-a189-4415-95c2-592ce235308d', 'test', '2020年02月17日', '100402207', 'root', 2);
INSERT INTO `folder` VALUES ('gongxiang', '共享文件夹', '--', '--', 'root', 3);
INSERT INTO `folder` VALUES ('root', 'ROOT', '--', 'admin', NULL, 0);
INSERT INTO `folder` VALUES ('thumbnail', 'THUMBNAIL', '--', '--', 'wstom', 0);
INSERT INTO `folder` VALUES ('wstom', 'WSTOM', '--', '--', '', 0);

SET FOREIGN_KEY_CHECKS = 1;
