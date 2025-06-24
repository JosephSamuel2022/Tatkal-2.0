package com.tatkal.utils;

import java.util.*;

public class TatkalSeatAssignment {

  static class SeatInfo {
    int value;
    int listIndex;
    int posInList;

    SeatInfo(int value, int listIndex, int posInList) {
      this.value = value;
      this.listIndex = listIndex;
      this.posInList = posInList;
    }
  }

  public static List<Integer> assignSeatsWithProximity(List<List<Integer>> seatTypes, List<String> passengerPreferences) {
    Map<String, Integer> seatTypeMap = new HashMap<>();
    seatTypeMap.put("lower", 0);
    seatTypeMap.put("middle", 1);
    seatTypeMap.put("upper", 2);

    List<Integer> mappedPreferences = new ArrayList<>();
    for (String pref : passengerPreferences) {
      Integer index = seatTypeMap.get(pref);
      if (index == null || seatTypes.get(index).isEmpty()) {
        int fallbackIndex = findFallbackIndex(seatTypes);
        mappedPreferences.add(fallbackIndex == -1 ? -1 : fallbackIndex);
      } else {
        mappedPreferences.add(index);
      }
    }

    Set<Integer> activeLists = new HashSet<>(mappedPreferences);
    activeLists.remove(-1);
    if (activeLists.isEmpty()) {
      return Collections.nCopies(passengerPreferences.size(), null);
    }

    // Step 1: Find best proximity range
    PriorityQueue<SeatInfo> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.value));
    int currentMax = Integer.MIN_VALUE;

    int[] pointers = new int[seatTypes.size()];

    for (int listIndex : activeLists) {
      int val = seatTypes.get(listIndex).get(0);
      minHeap.add(new SeatInfo(val, listIndex, 0));
      currentMax = Math.max(currentMax, val);
    }

    int minRange = Integer.MAX_VALUE;
    Map<Integer, Integer> bestPointers = new HashMap<>();

    while (true) {
      SeatInfo min = minHeap.poll();
      int currentMin = min.value;
      int diff = currentMax - currentMin;

      if (diff < minRange) {
        minRange = diff;
        bestPointers.clear();
        for (int i = 0; i < seatTypes.size(); i++) {
          bestPointers.put(i, pointers[i]);
        }
        bestPointers.put(min.listIndex, min.posInList);
      }

      pointers[min.listIndex] = min.posInList;

      if (min.posInList + 1 < seatTypes.get(min.listIndex).size()) {
        int nextVal = seatTypes.get(min.listIndex).get(min.posInList + 1);
        minHeap.add(new SeatInfo(nextVal, min.listIndex, min.posInList + 1));
        currentMax = Math.max(currentMax, nextVal);
      } else {
        break;
      }
    }

    // Phase 1: Assign from best proximity range
    Set<Integer> assignedSeats = new HashSet<>();
    List<Integer> result = new ArrayList<>();

    for (int i = 0; i < mappedPreferences.size(); i++) {
      int pref = mappedPreferences.get(i);
      Integer assignedSeat = null;

      if (pref != -1 && bestPointers.containsKey(pref)) {
        int pointer = bestPointers.get(pref);
        while (pointer < seatTypes.get(pref).size()) {
          int seat = seatTypes.get(pref).get(pointer);
          if (!assignedSeats.contains(seat)) {
            assignedSeat = seat;
            assignedSeats.add(seat);
            bestPointers.put(pref, pointer + 1);
            break;
          }
          pointer++;
        }
      }

      result.add(assignedSeat);
    }
// Phase 2: Assign leftover passengers with the smallest proximity to assigned seats
    for (int i = 0; i < result.size(); i++) {
      if (result.get(i) == null) {
        int pref = mappedPreferences.get(i);
        Integer assignedSeat = null;

        int minProximity = Integer.MAX_VALUE;
        int selectedSeat = -1;

        // 1️ Check preferred list first
        if (pref != -1) {
          for (int seat : seatTypes.get(pref)) {
            if (!assignedSeats.contains(seat)) {
              int proximity = assignedSeats.stream()
                .mapToInt(s -> Math.abs(seat - s))
                .min()
                .orElse(Integer.MAX_VALUE);

              if (proximity < minProximity ||
                (proximity == minProximity && seat < selectedSeat)) {
                minProximity = proximity;
                selectedSeat = seat;
              }
            }
          }
        }

        // 2️ Check other lists
        for (int j = 0; j < seatTypes.size(); j++) {
          if (j != pref) {
            for (int seat : seatTypes.get(j)) {
              if (!assignedSeats.contains(seat)) {
                int proximity = assignedSeats.stream()
                  .mapToInt(s -> Math.abs(seat - s))
                  .min()
                  .orElse(Integer.MAX_VALUE);

                if (proximity < minProximity ||
                  (proximity == minProximity && seat < selectedSeat)) {
                  minProximity = proximity;
                  selectedSeat = seat;
                }
              }
            }
          }
        }

        // 3️ Assign the best seat found
        if (selectedSeat != -1) {
          assignedSeat = selectedSeat;
          assignedSeats.add(assignedSeat);
        }

        result.set(i, assignedSeat);
      }
    }



    return result;
  }

  private static int findFallbackIndex(List<List<Integer>> seatTypes) {
    int fallbackIndex = -1;
    int maxSize = 0;
    for (int i = 0; i < seatTypes.size(); i++) {
      if (seatTypes.get(i).size() > maxSize) {
        maxSize = seatTypes.get(i).size();
        fallbackIndex = i;
      }
    }
    return fallbackIndex;
  }
}

