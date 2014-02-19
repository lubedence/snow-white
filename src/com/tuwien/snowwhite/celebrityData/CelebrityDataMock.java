/*******************************************************************************
 * Copyright (c) 2014 Lukas Furlan (furlan.lukas@gmail.com)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Lukas Furlan - initial API and implementation
 ******************************************************************************/
package com.tuwien.snowwhite.celebrityData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.tuwien.snowwhite.beautyCalculation.FacialFeatures;
import com.tuwien.snowwhite.beautyCalculation.FeaturePoints;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CelebrityDataMock implements ICelebrityData{
	
	private ArrayList<ICelebrity> celData = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataF = new ArrayList<ICelebrity>();
	private ArrayList<ICelebrity> celDataM = new ArrayList<ICelebrity>();
	
	public CelebrityDataMock(Context c){

		
		
				
		//create some celebrities
		String name = "Justin Bieber";
		FeaturePoints[] p = new FeaturePoints[]{new FeaturePoints(224,359),new FeaturePoints(229,398),new FeaturePoints(237,432),new FeaturePoints(250,467),new FeaturePoints(272,499),new FeaturePoints(301,522),new FeaturePoints(331,527),new FeaturePoints(359,521),new FeaturePoints(388,497),new FeaturePoints(411,463),new FeaturePoints(421,427),new FeaturePoints(426,392),new FeaturePoints(428,351),new FeaturePoints(367,260),new FeaturePoints(321,253),new FeaturePoints(275,264),new FeaturePoints(284,325),new FeaturePoints(262,325),new FeaturePoints(244,340),new FeaturePoints(264,337),new FeaturePoints(284,336),new FeaturePoints(305,336),new FeaturePoints(345,334),new FeaturePoints(366,321),new FeaturePoints(387,319),new FeaturePoints(406,332),new FeaturePoints(387,331),new FeaturePoints(367,332),new FeaturePoints(370,339),new FeaturePoints(279,343),new FeaturePoints(298,359),new FeaturePoints(288,352),new FeaturePoints(278,349),new FeaturePoints(270,352),new FeaturePoints(261,359),new FeaturePoints(270,364),new FeaturePoints(279,366),new FeaturePoints(288,364),new FeaturePoints(278,356),new FeaturePoints(370,352),new FeaturePoints(353,356),new FeaturePoints(362,348),new FeaturePoints(371,345),new FeaturePoints(380,347),new FeaturePoints(389,353),new FeaturePoints(381,359),new FeaturePoints(372,361),new FeaturePoints(363,360),new FeaturePoints(343,394),new FeaturePoints(328,394),new FeaturePoints(312,395),new FeaturePoints(313,425),new FeaturePoints(329,416),new FeaturePoints(345,423),new FeaturePoints(360,417),new FeaturePoints(351,426),new FeaturePoints(329,434),new FeaturePoints(307,428),new FeaturePoints(298,420),new FeaturePoints(292,463),new FeaturePoints(306,455),new FeaturePoints(320,451),new FeaturePoints(329,452),new FeaturePoints(338,451),new FeaturePoints(352,453),new FeaturePoints(367,461),new FeaturePoints(346,460),new FeaturePoints(329,461),new FeaturePoints(313,461),new FeaturePoints(313,466),new FeaturePoints(329,468),new FeaturePoints(346,465),new FeaturePoints(357,472),new FeaturePoints(345,478),new FeaturePoints(330,480),new FeaturePoints(315,479),new FeaturePoints(302,474)};
		Drawable pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 72.3f, pic, p));
		
		name = "Angelina Jolie";
		p = new FeaturePoints[]{new FeaturePoints(190,357),new FeaturePoints(194,409),new FeaturePoints(205,453),new FeaturePoints(221,494),new FeaturePoints(249,527),new FeaturePoints(280,555),new FeaturePoints(312,563),new FeaturePoints(351,554),new FeaturePoints(391,524),new FeaturePoints(419,487),new FeaturePoints(434,450),new FeaturePoints(442,408),new FeaturePoints(446,358),new FeaturePoints(370,233),new FeaturePoints(306,220),new FeaturePoints(244,230),new FeaturePoints(255,320),new FeaturePoints(229,314),new FeaturePoints(204,326),new FeaturePoints(228,326),new FeaturePoints(253,332),new FeaturePoints(281,337),new FeaturePoints(339,340),new FeaturePoints(367,323),new FeaturePoints(395,318),new FeaturePoints(422,332),new FeaturePoints(395,330),new FeaturePoints(369,335),new FeaturePoints(375,341),new FeaturePoints(248,338),new FeaturePoints(273,361),new FeaturePoints(261,350),new FeaturePoints(248,346),new FeaturePoints(236,349),new FeaturePoints(225,357),new FeaturePoints(236,366),new FeaturePoints(248,369),new FeaturePoints(261,367),new FeaturePoints(250,356),new FeaturePoints(376,358),new FeaturePoints(350,364),new FeaturePoints(363,353),new FeaturePoints(376,348),new FeaturePoints(388,352),new FeaturePoints(400,361),new FeaturePoints(388,368),new FeaturePoints(376,372),new FeaturePoints(363,370),new FeaturePoints(328,412),new FeaturePoints(308,411),new FeaturePoints(289,411),new FeaturePoints(288,444),new FeaturePoints(306,436),new FeaturePoints(327,445),new FeaturePoints(346,435),new FeaturePoints(335,448),new FeaturePoints(308,457),new FeaturePoints(283,446),new FeaturePoints(273,434),new FeaturePoints(261,483),new FeaturePoints(280,476),new FeaturePoints(297,474),new FeaturePoints(308,475),new FeaturePoints(320,474),new FeaturePoints(339,477),new FeaturePoints(361,483),new FeaturePoints(331,485),new FeaturePoints(309,486),new FeaturePoints(289,485),new FeaturePoints(289,492),new FeaturePoints(310,496),new FeaturePoints(332,492),new FeaturePoints(348,499),new FeaturePoints(331,508),new FeaturePoints(310,512),new FeaturePoints(290,509),new FeaturePoints(274,500)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 77.9f, pic, p));
		
		name = "Brad Pitt";
		p = new FeaturePoints[]{new FeaturePoints(201,376),new FeaturePoints(200,414),new FeaturePoints(206,451),new FeaturePoints(214,489),new FeaturePoints(243,534),new FeaturePoints(286,561),new FeaturePoints(319,565),new FeaturePoints(355,557),new FeaturePoints(396,523),new FeaturePoints(418,480),new FeaturePoints(422,441),new FeaturePoints(424,405),new FeaturePoints(420,369),new FeaturePoints(363,259),new FeaturePoints(308,250),new FeaturePoints(254,262),new FeaturePoints(265,340),new FeaturePoints(240,342),new FeaturePoints(221,360),new FeaturePoints(242,356),new FeaturePoints(264,354),new FeaturePoints(289,352),new FeaturePoints(335,351),new FeaturePoints(359,337),new FeaturePoints(385,338),new FeaturePoints(406,357),new FeaturePoints(384,353),new FeaturePoints(360,352),new FeaturePoints(366,356),new FeaturePoints(259,358),new FeaturePoints(278,374),new FeaturePoints(268,366),new FeaturePoints(259,363),new FeaturePoints(249,367),new FeaturePoints(240,374),new FeaturePoints(250,379),new FeaturePoints(259,380),new FeaturePoints(269,378),new FeaturePoints(260,371),new FeaturePoints(368,368),new FeaturePoints(347,372),new FeaturePoints(357,364),new FeaturePoints(367,360),new FeaturePoints(376,363),new FeaturePoints(385,371),new FeaturePoints(376,375),new FeaturePoints(367,377),new FeaturePoints(357,376),new FeaturePoints(328,408),new FeaturePoints(312,409),new FeaturePoints(295,410),new FeaturePoints(295,441),new FeaturePoints(313,431),new FeaturePoints(332,440),new FeaturePoints(346,427),new FeaturePoints(341,444),new FeaturePoints(314,451),new FeaturePoints(286,446),new FeaturePoints(279,429),new FeaturePoints(269,487),new FeaturePoints(287,478),new FeaturePoints(304,474),new FeaturePoints(315,476),new FeaturePoints(325,473),new FeaturePoints(343,475),new FeaturePoints(362,482),new FeaturePoints(337,482),new FeaturePoints(315,484),new FeaturePoints(293,484),new FeaturePoints(293,490),new FeaturePoints(315,492),new FeaturePoints(337,488),new FeaturePoints(350,494),new FeaturePoints(334,501),new FeaturePoints(316,504),new FeaturePoints(297,504),new FeaturePoints(281,498)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 79.1f, pic, p));
		
		name = "Lady Gaga";
		p = new FeaturePoints[]{new FeaturePoints(209,331),new FeaturePoints(209,369),new FeaturePoints(215,405),new FeaturePoints(224,446),new FeaturePoints(243,479),new FeaturePoints(273,507),new FeaturePoints(306,514),new FeaturePoints(337,509),new FeaturePoints(371,485),new FeaturePoints(398,448),new FeaturePoints(407,409),new FeaturePoints(415,371),new FeaturePoints(416,329),new FeaturePoints(360,225),new FeaturePoints(310,217),new FeaturePoints(259,227),new FeaturePoints(264,286),new FeaturePoints(238,288),new FeaturePoints(218,310),new FeaturePoints(240,300),new FeaturePoints(263,299),new FeaturePoints(288,301),new FeaturePoints(335,298),new FeaturePoints(359,284),new FeaturePoints(385,285),new FeaturePoints(405,305),new FeaturePoints(383,296),new FeaturePoints(360,296),new FeaturePoints(361,308),new FeaturePoints(259,310),new FeaturePoints(280,332),new FeaturePoints(270,323),new FeaturePoints(259,319),new FeaturePoints(249,323),new FeaturePoints(239,332),new FeaturePoints(249,338),new FeaturePoints(259,341),new FeaturePoints(270,338),new FeaturePoints(258,329),new FeaturePoints(359,326),new FeaturePoints(340,331),new FeaturePoints(351,321),new FeaturePoints(361,317),new FeaturePoints(372,320),new FeaturePoints(382,329),new FeaturePoints(372,336),new FeaturePoints(362,338),new FeaturePoints(351,336),new FeaturePoints(328,370),new FeaturePoints(312,369),new FeaturePoints(296,368),new FeaturePoints(295,399),new FeaturePoints(311,388),new FeaturePoints(326,400),new FeaturePoints(343,395),new FeaturePoints(334,405),new FeaturePoints(310,410),new FeaturePoints(288,405),new FeaturePoints(280,395),new FeaturePoints(267,445),new FeaturePoints(284,435),new FeaturePoints(299,430),new FeaturePoints(309,432),new FeaturePoints(320,431),new FeaturePoints(335,435),new FeaturePoints(351,446),new FeaturePoints(327,444),new FeaturePoints(309,443),new FeaturePoints(290,443),new FeaturePoints(290,451),new FeaturePoints(309,454),new FeaturePoints(327,451),new FeaturePoints(340,460),new FeaturePoints(326,468),new FeaturePoints(308,470),new FeaturePoints(291,467),new FeaturePoints(277,459)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 68.5f, pic, p));
		
		name = "Miley Cyrus";
		p = new FeaturePoints[]{new FeaturePoints(197,414),new FeaturePoints(199,463),new FeaturePoints(209,506),new FeaturePoints(223,545),new FeaturePoints(246,582),new FeaturePoints(279,617),new FeaturePoints(314,627),new FeaturePoints(350,620),new FeaturePoints(395,589),new FeaturePoints(430,547),new FeaturePoints(444,504),new FeaturePoints(451,458),new FeaturePoints(451,410),new FeaturePoints(375,297),new FeaturePoints(317,287),new FeaturePoints(260,299),new FeaturePoints(256,376),new FeaturePoints(226,371),new FeaturePoints(200,389),new FeaturePoints(226,386),new FeaturePoints(254,390),new FeaturePoints(285,394),new FeaturePoints(337,393),new FeaturePoints(365,373),new FeaturePoints(396,367),new FeaturePoints(426,383),new FeaturePoints(397,381),new FeaturePoints(368,388),new FeaturePoints(373,393),new FeaturePoints(255,395),new FeaturePoints(281,419),new FeaturePoints(268,409),new FeaturePoints(256,404),new FeaturePoints(244,408),new FeaturePoints(232,416),new FeaturePoints(244,425),new FeaturePoints(255,428),new FeaturePoints(268,426),new FeaturePoints(258,415),new FeaturePoints(373,413),new FeaturePoints(348,418),new FeaturePoints(361,407),new FeaturePoints(373,402),new FeaturePoints(386,406),new FeaturePoints(398,413),new FeaturePoints(387,423),new FeaturePoints(375,427),new FeaturePoints(361,424),new FeaturePoints(332,471),new FeaturePoints(313,471),new FeaturePoints(294,471),new FeaturePoints(293,505),new FeaturePoints(310,496),new FeaturePoints(331,504),new FeaturePoints(348,491),new FeaturePoints(339,506),new FeaturePoints(312,516),new FeaturePoints(288,506),new FeaturePoints(280,491),new FeaturePoints(269,540),new FeaturePoints(286,533),new FeaturePoints(302,530),new FeaturePoints(313,532),new FeaturePoints(323,530),new FeaturePoints(342,533),new FeaturePoints(362,540),new FeaturePoints(335,542),new FeaturePoints(313,543),new FeaturePoints(294,542),new FeaturePoints(293,547),new FeaturePoints(313,551),new FeaturePoints(335,547),new FeaturePoints(350,556),new FeaturePoints(333,565),new FeaturePoints(313,568),new FeaturePoints(295,565),new FeaturePoints(280,555)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 76.3f, pic, p));
		
		name = "Katy Perry";
		p = new FeaturePoints[]{new FeaturePoints(242,340),new FeaturePoints(241,369),new FeaturePoints(246,398),new FeaturePoints(255,428),new FeaturePoints(272,454),new FeaturePoints(298,477),new FeaturePoints(321,483),new FeaturePoints(344,477),new FeaturePoints(370,454),new FeaturePoints(389,427),new FeaturePoints(396,397),new FeaturePoints(401,369),new FeaturePoints(399,339),new FeaturePoints(358,262),new FeaturePoints(318,254),new FeaturePoints(279,262),new FeaturePoints(283,316),new FeaturePoints(265,313),new FeaturePoints(250,323),new FeaturePoints(265,322),new FeaturePoints(282,325),new FeaturePoints(300,326),new FeaturePoints(340,326),new FeaturePoints(357,315),new FeaturePoints(375,312),new FeaturePoints(391,322),new FeaturePoints(375,320),new FeaturePoints(359,324),new FeaturePoints(359,329),new FeaturePoints(280,330),new FeaturePoints(296,343),new FeaturePoints(288,337),new FeaturePoints(280,334),new FeaturePoints(273,337),new FeaturePoints(266,342),new FeaturePoints(273,347),new FeaturePoints(280,349),new FeaturePoints(288,347),new FeaturePoints(281,341),new FeaturePoints(359,340),new FeaturePoints(344,343),new FeaturePoints(352,337),new FeaturePoints(359,334),new FeaturePoints(367,336),new FeaturePoints(374,341),new FeaturePoints(367,346),new FeaturePoints(360,349),new FeaturePoints(352,347),new FeaturePoints(332,376),new FeaturePoints(321,376),new FeaturePoints(309,376),new FeaturePoints(309,398),new FeaturePoints(321,392),new FeaturePoints(332,398),new FeaturePoints(341,389),new FeaturePoints(336,400),new FeaturePoints(321,405),new FeaturePoints(306,400),new FeaturePoints(300,388),new FeaturePoints(295,426),new FeaturePoints(305,421),new FeaturePoints(314,418),new FeaturePoints(321,420),new FeaturePoints(327,418),new FeaturePoints(337,421),new FeaturePoints(347,426),new FeaturePoints(333,426),new FeaturePoints(321,427),new FeaturePoints(309,426),new FeaturePoints(308,429),new FeaturePoints(321,432),new FeaturePoints(333,430),new FeaturePoints(341,435),new FeaturePoints(332,440),new FeaturePoints(321,443),new FeaturePoints(309,440),new FeaturePoints(301,435)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 74.2f, pic, p));
		
		name = "Robert Redford";
		p = new FeaturePoints[]{new FeaturePoints(201,399),new FeaturePoints(199,439),new FeaturePoints(204,481),new FeaturePoints(215,529),new FeaturePoints(246,577),new FeaturePoints(294,610),new FeaturePoints(334,616),new FeaturePoints(374,609),new FeaturePoints(420,574),new FeaturePoints(447,527),new FeaturePoints(456,483),new FeaturePoints(460,440),new FeaturePoints(458,397),new FeaturePoints(385,281),new FeaturePoints(327,269),new FeaturePoints(269,281),new FeaturePoints(283,374),new FeaturePoints(252,369),new FeaturePoints(224,387),new FeaturePoints(252,386),new FeaturePoints(280,390),new FeaturePoints(310,391),new FeaturePoints(353,391),new FeaturePoints(382,373),new FeaturePoints(413,369),new FeaturePoints(439,387),new FeaturePoints(413,386),new FeaturePoints(384,389),new FeaturePoints(387,383),new FeaturePoints(271,383),new FeaturePoints(295,402),new FeaturePoints(283,392),new FeaturePoints(271,388),new FeaturePoints(260,391),new FeaturePoints(248,400),new FeaturePoints(260,406),new FeaturePoints(271,409),new FeaturePoints(283,407),new FeaturePoints(270,397),new FeaturePoints(386,397),new FeaturePoints(364,402),new FeaturePoints(376,392),new FeaturePoints(387,387),new FeaturePoints(399,391),new FeaturePoints(410,399),new FeaturePoints(399,406),new FeaturePoints(388,409),new FeaturePoints(376,407),new FeaturePoints(349,443),new FeaturePoints(331,443),new FeaturePoints(313,443),new FeaturePoints(311,480),new FeaturePoints(332,470),new FeaturePoints(352,480),new FeaturePoints(370,468),new FeaturePoints(361,485),new FeaturePoints(332,493),new FeaturePoints(302,486),new FeaturePoints(293,469),new FeaturePoints(283,529),new FeaturePoints(302,518),new FeaturePoints(321,514),new FeaturePoints(332,516),new FeaturePoints(344,514),new FeaturePoints(362,517),new FeaturePoints(381,528),new FeaturePoints(355,526),new FeaturePoints(332,527),new FeaturePoints(310,527),new FeaturePoints(309,533),new FeaturePoints(332,535),new FeaturePoints(355,532),new FeaturePoints(369,542),new FeaturePoints(353,550),new FeaturePoints(333,552),new FeaturePoints(312,551),new FeaturePoints(295,543)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 75.4f, pic, p));
		
		name = "Barack Obama";
		p = new FeaturePoints[]{new FeaturePoints(247,342),new FeaturePoints(251,375),new FeaturePoints(256,405),new FeaturePoints(266,435),new FeaturePoints(285,467),new FeaturePoints(309,490),new FeaturePoints(334,494),new FeaturePoints(362,490),new FeaturePoints(390,468),new FeaturePoints(412,440),new FeaturePoints(422,404),new FeaturePoints(427,373),new FeaturePoints(429,340),new FeaturePoints(386,253),new FeaturePoints(344,245),new FeaturePoints(301,255),new FeaturePoints(299,319),new FeaturePoints(278,320),new FeaturePoints(263,335),new FeaturePoints(280,333),new FeaturePoints(298,331),new FeaturePoints(318,329),new FeaturePoints(355,327),new FeaturePoints(376,316),new FeaturePoints(397,316),new FeaturePoints(413,332),new FeaturePoints(395,329),new FeaturePoints(376,328),new FeaturePoints(380,330),new FeaturePoints(296,332),new FeaturePoints(310,344),new FeaturePoints(303,338),new FeaturePoints(295,336),new FeaturePoints(288,338),new FeaturePoints(280,343),new FeaturePoints(288,346),new FeaturePoints(295,348),new FeaturePoints(303,347),new FeaturePoints(296,341),new FeaturePoints(382,338),new FeaturePoints(364,341),new FeaturePoints(372,336),new FeaturePoints(380,333),new FeaturePoints(387,336),new FeaturePoints(394,340),new FeaturePoints(387,344),new FeaturePoints(380,345),new FeaturePoints(372,344),new FeaturePoints(348,373),new FeaturePoints(334,374),new FeaturePoints(320,375),new FeaturePoints(317,403),new FeaturePoints(333,397),new FeaturePoints(351,402),new FeaturePoints(365,395),new FeaturePoints(358,403),new FeaturePoints(334,412),new FeaturePoints(311,405),new FeaturePoints(304,395),new FeaturePoints(297,436),new FeaturePoints(312,431),new FeaturePoints(325,429),new FeaturePoints(334,431),new FeaturePoints(342,429),new FeaturePoints(357,430),new FeaturePoints(374,433),new FeaturePoints(351,435),new FeaturePoints(334,437),new FeaturePoints(317,437),new FeaturePoints(317,438),new FeaturePoints(334,440),new FeaturePoints(351,437),new FeaturePoints(362,443),new FeaturePoints(348,448),new FeaturePoints(334,450),new FeaturePoints(319,450),new FeaturePoints(307,445)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 68.4f, pic, p));
		
		name = "Steve Buscemi";
		p = new FeaturePoints[]{new FeaturePoints(192,352),new FeaturePoints(192,392),new FeaturePoints(198,430),new FeaturePoints(210,474),new FeaturePoints(237,516),new FeaturePoints(278,544),new FeaturePoints(315,550),new FeaturePoints(351,544),new FeaturePoints(394,518),new FeaturePoints(423,477),new FeaturePoints(434,434),new FeaturePoints(439,393),new FeaturePoints(439,348),new FeaturePoints(371,227),new FeaturePoints(311,216),new FeaturePoints(252,230),new FeaturePoints(265,318),new FeaturePoints(239,316),new FeaturePoints(216,333),new FeaturePoints(239,330),new FeaturePoints(263,331),new FeaturePoints(288,332),new FeaturePoints(336,330),new FeaturePoints(361,316),new FeaturePoints(388,314),new FeaturePoints(411,331),new FeaturePoints(387,328),new FeaturePoints(362,329),new FeaturePoints(370,332),new FeaturePoints(252,335),new FeaturePoints(274,352),new FeaturePoints(263,344),new FeaturePoints(251,340),new FeaturePoints(241,344),new FeaturePoints(231,352),new FeaturePoints(242,358),new FeaturePoints(252,360),new FeaturePoints(263,358),new FeaturePoints(250,349),new FeaturePoints(370,346),new FeaturePoints(348,351),new FeaturePoints(359,341),new FeaturePoints(370,337),new FeaturePoints(381,341),new FeaturePoints(392,349),new FeaturePoints(381,355),new FeaturePoints(371,358),new FeaturePoints(359,356),new FeaturePoints(331,392),new FeaturePoints(313,392),new FeaturePoints(295,392),new FeaturePoints(294,425),new FeaturePoints(312,414),new FeaturePoints(332,425),new FeaturePoints(349,415),new FeaturePoints(340,431),new FeaturePoints(313,437),new FeaturePoints(287,432),new FeaturePoints(278,416),new FeaturePoints(270,476),new FeaturePoints(287,468),new FeaturePoints(303,464),new FeaturePoints(314,465),new FeaturePoints(326,463),new FeaturePoints(343,467),new FeaturePoints(361,475),new FeaturePoints(335,473),new FeaturePoints(314,474),new FeaturePoints(294,474),new FeaturePoints(294,478),new FeaturePoints(314,480),new FeaturePoints(335,477),new FeaturePoints(348,486),new FeaturePoints(333,492),new FeaturePoints(314,494),new FeaturePoints(297,492),new FeaturePoints(282,487)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 64.7f, pic, p));
		
		name = "Angela Merkel";
		p = new FeaturePoints[]{new FeaturePoints(223,352),new FeaturePoints(221,386),new FeaturePoints(225,418),new FeaturePoints(233,454),new FeaturePoints(256,488),new FeaturePoints(287,513),new FeaturePoints(314,519),new FeaturePoints(341,514),new FeaturePoints(370,491),new FeaturePoints(391,458),new FeaturePoints(401,428),new FeaturePoints(408,397),new FeaturePoints(411,362),new FeaturePoints(363,270),new FeaturePoints(319,258),new FeaturePoints(275,264),new FeaturePoints(289,328),new FeaturePoints(270,325),new FeaturePoints(251,335),new FeaturePoints(269,336),new FeaturePoints(287,339),new FeaturePoints(306,340),new FeaturePoints(341,343),new FeaturePoints(359,334),new FeaturePoints(378,334),new FeaturePoints(393,345),new FeaturePoints(377,344),new FeaturePoints(360,344),new FeaturePoints(365,349),new FeaturePoints(277,343),new FeaturePoints(293,356),new FeaturePoints(285,350),new FeaturePoints(277,347),new FeaturePoints(269,349),new FeaturePoints(261,354),new FeaturePoints(269,359),new FeaturePoints(276,361),new FeaturePoints(284,359),new FeaturePoints(275,353),new FeaturePoints(364,359),new FeaturePoints(348,360),new FeaturePoints(357,355),new FeaturePoints(365,353),new FeaturePoints(372,355),new FeaturePoints(380,362),new FeaturePoints(372,365),new FeaturePoints(364,366),new FeaturePoints(356,364),new FeaturePoints(336,391),new FeaturePoints(322,390),new FeaturePoints(309,389),new FeaturePoints(307,414),new FeaturePoints(323,408),new FeaturePoints(335,417),new FeaturePoints(346,408),new FeaturePoints(339,421),new FeaturePoints(320,424),new FeaturePoints(301,418),new FeaturePoints(295,405),new FeaturePoints(285,452),new FeaturePoints(299,447),new FeaturePoints(311,445),new FeaturePoints(319,447),new FeaturePoints(327,446),new FeaturePoints(338,450),new FeaturePoints(348,457),new FeaturePoints(332,454),new FeaturePoints(318,453),new FeaturePoints(304,451),new FeaturePoints(303,454),new FeaturePoints(318,458),new FeaturePoints(332,457),new FeaturePoints(339,464),new FeaturePoints(329,466),new FeaturePoints(317,466),new FeaturePoints(305,464),new FeaturePoints(294,460)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 70.6f, pic, p));
		
		name = "Nicolas Cage";
		p = new FeaturePoints[]{new FeaturePoints(170,379),new FeaturePoints(169,430),new FeaturePoints(174,478),new FeaturePoints(185,532),new FeaturePoints(220,584),new FeaturePoints(272,619),new FeaturePoints(315,626),new FeaturePoints(353,620),new FeaturePoints(393,590),new FeaturePoints(426,543),new FeaturePoints(441,493),new FeaturePoints(452,446),new FeaturePoints(456,393),new FeaturePoints(391,259),new FeaturePoints(328,243),new FeaturePoints(264,253),new FeaturePoints(281,341),new FeaturePoints(248,342),new FeaturePoints(220,364),new FeaturePoints(249,360),new FeaturePoints(279,358),new FeaturePoints(308,358),new FeaturePoints(355,360),new FeaturePoints(382,346),new FeaturePoints(410,349),new FeaturePoints(432,373),new FeaturePoints(408,367),new FeaturePoints(382,363),new FeaturePoints(388,369),new FeaturePoints(264,364),new FeaturePoints(287,384),new FeaturePoints(275,375),new FeaturePoints(263,371),new FeaturePoints(251,374),new FeaturePoints(239,383),new FeaturePoints(251,389),new FeaturePoints(262,392),new FeaturePoints(274,390),new FeaturePoints(260,380),new FeaturePoints(386,386),new FeaturePoints(364,388),new FeaturePoints(376,380),new FeaturePoints(389,377),new FeaturePoints(399,381),new FeaturePoints(410,390),new FeaturePoints(398,396),new FeaturePoints(387,398),new FeaturePoints(376,395),new FeaturePoints(351,436),new FeaturePoints(331,435),new FeaturePoints(311,434),new FeaturePoints(309,474),new FeaturePoints(334,462),new FeaturePoints(351,477),new FeaturePoints(366,466),new FeaturePoints(357,484),new FeaturePoints(329,489),new FeaturePoints(298,481),new FeaturePoints(289,463),new FeaturePoints(272,534),new FeaturePoints(295,525),new FeaturePoints(314,521),new FeaturePoints(327,524),new FeaturePoints(339,523),new FeaturePoints(354,529),new FeaturePoints(367,541),new FeaturePoints(345,535),new FeaturePoints(325,533),new FeaturePoints(301,532),new FeaturePoints(301,536),new FeaturePoints(323,540),new FeaturePoints(344,538),new FeaturePoints(355,551),new FeaturePoints(340,555),new FeaturePoints(322,555),new FeaturePoints(302,552),new FeaturePoints(286,545)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 71.3f, pic, p));
		
		name = "Bill Gates";
		p = new FeaturePoints[]{new FeaturePoints(239,316),new FeaturePoints(237,346),new FeaturePoints(241,374),new FeaturePoints(247,401),new FeaturePoints(264,431),new FeaturePoints(290,453),new FeaturePoints(312,458),new FeaturePoints(337,454),new FeaturePoints(364,432),new FeaturePoints(383,407),new FeaturePoints(391,375),new FeaturePoints(395,347),new FeaturePoints(393,320),new FeaturePoints(365,235),new FeaturePoints(325,226),new FeaturePoints(283,234),new FeaturePoints(285,294),new FeaturePoints(269,293),new FeaturePoints(254,302),new FeaturePoints(269,302),new FeaturePoints(284,303),new FeaturePoints(300,304),new FeaturePoints(334,303),new FeaturePoints(349,294),new FeaturePoints(366,293),new FeaturePoints(380,303),new FeaturePoints(365,302),new FeaturePoints(350,303),new FeaturePoints(358,307),new FeaturePoints(278,307),new FeaturePoints(292,320),new FeaturePoints(285,314),new FeaturePoints(278,311),new FeaturePoints(270,313),new FeaturePoints(263,318),new FeaturePoints(270,322),new FeaturePoints(277,324),new FeaturePoints(284,323),new FeaturePoints(279,317),new FeaturePoints(361,317),new FeaturePoints(343,320),new FeaturePoints(351,313),new FeaturePoints(358,311),new FeaturePoints(365,314),new FeaturePoints(372,319),new FeaturePoints(365,323),new FeaturePoints(358,324),new FeaturePoints(351,323),new FeaturePoints(326,352),new FeaturePoints(314,353),new FeaturePoints(302,353),new FeaturePoints(301,375),new FeaturePoints(313,369),new FeaturePoints(326,375),new FeaturePoints(336,366),new FeaturePoints(331,377),new FeaturePoints(313,383),new FeaturePoints(296,377),new FeaturePoints(292,364),new FeaturePoints(282,403),new FeaturePoints(294,401),new FeaturePoints(305,400),new FeaturePoints(313,401),new FeaturePoints(321,399),new FeaturePoints(333,401),new FeaturePoints(346,403),new FeaturePoints(328,404),new FeaturePoints(313,405),new FeaturePoints(298,405),new FeaturePoints(298,407),new FeaturePoints(313,409),new FeaturePoints(328,407),new FeaturePoints(336,410),new FeaturePoints(325,415),new FeaturePoints(313,416),new FeaturePoints(300,415),new FeaturePoints(290,411)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 69.7f, pic, p));
		
		name = "George Clooney";
		p = new FeaturePoints[]{new FeaturePoints(227,337),new FeaturePoints(227,368),new FeaturePoints(233,400),new FeaturePoints(242,436),new FeaturePoints(264,472),new FeaturePoints(292,499),new FeaturePoints(319,504),new FeaturePoints(347,498),new FeaturePoints(378,470),new FeaturePoints(400,434),new FeaturePoints(408,400),new FeaturePoints(414,368),new FeaturePoints(415,336),new FeaturePoints(364,243),new FeaturePoints(318,234),new FeaturePoints(273,244),new FeaturePoints(285,314),new FeaturePoints(262,312),new FeaturePoints(242,325),new FeaturePoints(262,325),new FeaturePoints(284,327),new FeaturePoints(305,327),new FeaturePoints(338,327),new FeaturePoints(360,314),new FeaturePoints(384,312),new FeaturePoints(404,326),new FeaturePoints(384,324),new FeaturePoints(361,326),new FeaturePoints(366,324),new FeaturePoints(275,324),new FeaturePoints(293,339),new FeaturePoints(284,332),new FeaturePoints(275,329),new FeaturePoints(266,332),new FeaturePoints(258,338),new FeaturePoints(266,343),new FeaturePoints(275,345),new FeaturePoints(284,343),new FeaturePoints(275,336),new FeaturePoints(366,335),new FeaturePoints(348,338),new FeaturePoints(357,332),new FeaturePoints(366,329),new FeaturePoints(375,331),new FeaturePoints(383,338),new FeaturePoints(375,342),new FeaturePoints(366,344),new FeaturePoints(357,343),new FeaturePoints(333,374),new FeaturePoints(320,374),new FeaturePoints(307,374),new FeaturePoints(305,402),new FeaturePoints(319,396),new FeaturePoints(334,402),new FeaturePoints(344,391),new FeaturePoints(339,404),new FeaturePoints(319,412),new FeaturePoints(300,403),new FeaturePoints(295,391),new FeaturePoints(279,433),new FeaturePoints(296,433),new FeaturePoints(309,432),new FeaturePoints(319,434),new FeaturePoints(328,432),new FeaturePoints(342,433),new FeaturePoints(358,433),new FeaturePoints(337,435),new FeaturePoints(318,437),new FeaturePoints(300,435),new FeaturePoints(300,439),new FeaturePoints(318,441),new FeaturePoints(337,438),new FeaturePoints(346,441),new FeaturePoints(333,446),new FeaturePoints(318,448),new FeaturePoints(303,446),new FeaturePoints(291,442)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "male", 81.2f, pic, p));
		
		name = "Marilyn Monroe";
		p = new FeaturePoints[]{new FeaturePoints(222,387),new FeaturePoints(222,416),new FeaturePoints(226,445),new FeaturePoints(234,479),new FeaturePoints(253,513),new FeaturePoints(282,541),new FeaturePoints(312,549),new FeaturePoints(341,543),new FeaturePoints(375,519),new FeaturePoints(399,483),new FeaturePoints(408,453),new FeaturePoints(413,422),new FeaturePoints(415,389),new FeaturePoints(359,293),new FeaturePoints(312,282),new FeaturePoints(264,291),new FeaturePoints(268,348),new FeaturePoints(246,346),new FeaturePoints(229,360),new FeaturePoints(247,356),new FeaturePoints(266,359),new FeaturePoints(288,362),new FeaturePoints(338,362),new FeaturePoints(360,349),new FeaturePoints(382,348),new FeaturePoints(400,362),new FeaturePoints(381,357),new FeaturePoints(361,360),new FeaturePoints(361,374),new FeaturePoints(264,372),new FeaturePoints(283,389),new FeaturePoints(273,382),new FeaturePoints(264,380),new FeaturePoints(255,382),new FeaturePoints(247,388),new FeaturePoints(255,393),new FeaturePoints(264,396),new FeaturePoints(273,394),new FeaturePoints(264,387),new FeaturePoints(358,388),new FeaturePoints(343,391),new FeaturePoints(352,384),new FeaturePoints(361,381),new FeaturePoints(370,383),new FeaturePoints(379,389),new FeaturePoints(370,395),new FeaturePoints(361,397),new FeaturePoints(352,395),new FeaturePoints(329,417),new FeaturePoints(314,416),new FeaturePoints(299,415),new FeaturePoints(297,442),new FeaturePoints(313,434),new FeaturePoints(329,443),new FeaturePoints(345,436),new FeaturePoints(336,447),new FeaturePoints(313,452),new FeaturePoints(291,446),new FeaturePoints(283,436),new FeaturePoints(274,477),new FeaturePoints(290,470),new FeaturePoints(304,467),new FeaturePoints(313,468),new FeaturePoints(322,468),new FeaturePoints(337,471),new FeaturePoints(353,479),new FeaturePoints(331,478),new FeaturePoints(313,479),new FeaturePoints(295,478),new FeaturePoints(295,483),new FeaturePoints(313,486),new FeaturePoints(331,484),new FeaturePoints(343,491),new FeaturePoints(329,497),new FeaturePoints(312,499),new FeaturePoints(297,496),new FeaturePoints(284,489)};
		pic = c.getResources().getDrawable(c.getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", c.getPackageName()));
		celData.add(new Celebrity(name, "female", 72.0f, pic, p));
		
		pic = null;
		
		
		//order in descending order
		Collections.sort(celData, new Comparator<ICelebrity>(){
			 public int compare(ICelebrity c1, ICelebrity c2) {
				 return Float.compare(c2.getScore(), c1.getScore());
			    }
		});
		
		for(ICelebrity cc: celData){
			Log.d("DESC::::", cc.getScore()+"");
		}
	}

	@Override
	public ArrayList<ICelebrity> getAllCelebritiesDescOrder() {
		return celData;
	}

	@Override
	public ArrayList<ICelebrity> getAllFemaleCelebritiesDescOrder() {
		if(celDataF.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "female")
					celDataF.add(c);
			}
		return celDataF;
	}

	@Override
	public ArrayList<ICelebrity> getAllMaleCelebritiesDescOrder() {
		if(celDataM.size() == 0)
			for(ICelebrity c: celData){
				if(c.getSex() == "male")
					celDataM.add(c);
			}
		return celDataM;
	}

	@Override
	public ArrayList<ICelebrity> getCelebritiesDescOrder(FeaturePoints[] featurePoints, int bestX) {
		SimilarityCalculation sc = new SimilarityCalculation(featurePoints);
		float[] diff = new float[celData.size()];
		
		for(int i=0; i<celData.size(); i++){
			diff[i] = sc.getSimilarity(celData.get(i).getPoints());
		}
		
		ArrayList<ICelebrity> similarCelebs = new ArrayList<ICelebrity>();
		float bestMatch = 0;
		int n = 0;
		
		if(bestX>diff.length)
			bestX=diff.length;
		
		for(int j=0; j<bestX; j++){
			for(int i=0; i<diff.length; i++){
				if(Float.compare(diff[i],bestMatch) > 0){
					bestMatch = diff[i];
					n = i;
				}
			}
			ICelebrity c = celData.get(n);
			c.setScore((bestMatch/sc.usedFeatureAmount())*100);
			similarCelebs.add(c);
			bestMatch = 0;
			diff[n] = 0;
		}
		
		
		return similarCelebs;
	}
	
	

}
