// src/main/java/com/example/demo/service/RankingService.java (修正版)

package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RankingDto;
import com.example.demo.model.Medal;
import com.example.demo.repository.MedalRepository;

@Service
@Transactional
public class RankingService {

    private final MedalRepository medalRepository;

    // コンストラクタインジェクション
    public RankingService(MedalRepository medalRepository) {
        this.medalRepository = medalRepository;
    }

    /**
     * 上位50人のランキングリスト（順位付き）を取得します。
     * @param currentLoginId ログイン中のユーザーID
     * @return ランキングDTOのリスト
     */
    public List<RankingDto> getFullRankingList(String currentLoginId) {
        List<Medal> medals = medalRepository.findTop50ByOrderByMyMedalDesc(); 
        
        List<RankingDto> rankingList = new ArrayList<>();
        
        // 順位を計算しながらDTOに変換
        // 同率順位のロジックは簡略化し、インデックス+1を順位としています。
        for (int i = 0; i < medals.size(); i++) {
            Medal medal = medals.get(i);
            int rank = i + 1;
            
            // ログインユーザー自身かどうかを判定し、DTOにフラグを立てる
            boolean isMe = medal.getLoginID().equals(currentLoginId);
            
            RankingDto dto = new RankingDto(
                rank, 
                medal.getNickname(), 
                medal.getMyMedal(), 
                isMe
            );
            rankingList.add(dto);
        }
        
        return rankingList;
    }

    /**
     * ログイン中のユーザーのメダル情報（My Medals表示用）を取得します。
     * @param currentLoginId ログイン中のユーザーID
     * @return ユーザーのメダル所持数。情報がない場合は0を返します。
     */
    public int getMyMedals(String currentLoginId) {
        //
        Optional<Medal> optionalMedal = medalRepository.findByLoginId(currentLoginId);
        
        return optionalMedal.map(Medal::getMyMedal).orElse(0);
    }

    /**
     * ログイン中のユーザーのランキング情報（画面下部の固定表示用）を取得します。
     * @param currentLoginId ログイン中のユーザーID
     * @param fullRankingList getFullRankingListで取得した上位50人のリスト
     * @return 自分のランキング情報。見つからない場合はnullを返します。
     */
    public RankingDto getMyRankingInfo(String currentLoginId, List<RankingDto> fullRankingList) {
        // ランキングリスト（Top 50）の中に自分がいればそのDTOを返却
        Optional<RankingDto> foundInTop50 = fullRankingList.stream()
                .filter(dto -> dto.isMe()) // isMeフラグが立っているものを利用
                .findFirst();

        if (foundInTop50.isPresent()) {
            return foundInTop50.get(); // Top 50にいた場合はその情報を利用
        } else {
            // Top 50に見つからなかった場合（圏外の場合）
            Optional<Medal> myMedalOpt = medalRepository.findByLoginId(currentLoginId);
            if (myMedalOpt.isPresent()) {
                 Medal myMedal = myMedalOpt.get();
                 return new RankingDto(
                     0, // 順位0は「圏外」としてHTML側で表示を制御
                     "Me", // 画面表示用に「Me」など固定のニックネームを設定（HTMLで上書き）
                     myMedal.getMyMedal(), 
                     true
                 );
            }
            return null; // そもそもメダル情報が存在しない
        }
    }
}