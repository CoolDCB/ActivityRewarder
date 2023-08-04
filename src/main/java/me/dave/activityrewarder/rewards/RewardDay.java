package me.dave.activityrewarder.rewards;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RewardDay {
    private final List<DailyRewardCollection> dailyRewardCollections = new ArrayList<>();

    @Nullable
    public DailyRewardCollection getHighestPriorityRewards() {
        return dailyRewardCollections.stream().min(Comparator.comparingInt(DailyRewardCollection::priority)).orElse(null);
    }
}
