import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Queue;
import java.util.Random;

public class PageReplacementAlgorithms {
    //FIFO
    public static int fifoPageReplacement(int[] pages, int cacheSize) {
        LinkedList<Integer> cache = new LinkedList<>();

        int pageFaults = 0;
        for (int page : pages) {
            if (!cache.contains(page)) {
                pageFaults++;
                if (cache.size() == cacheSize) {
                    cache.removeFirst();
                }
                cache.addLast(page);
            }
        }

        return pageFaults;
    }

    public static int lruPageReplacement(int[] pages, int cacheSize) {
        Set<Integer> cache = new LinkedHashSet<>();

        int pageFaults = 0;
        for (int page : pages) {
            if (!cache.contains(page)) {
                pageFaults++;
                if (cache.size() == cacheSize) {
                    // Usuń najstarszą (najmniej używaną) stronę
                    int oldestPage = cache.iterator().next();
                    cache.remove(oldestPage);
                }
                cache.add(page);
            } else {
                // Odśwież kolejność, przenosząc stronę na koniec zbioru
                cache.remove(page);
                cache.add(page);
            }
        }

        return pageFaults;
    }
//Second Chance
    public static int secondChancePageReplacement(int[] pages, int cacheSize) {
        Queue<Integer> cache = new LinkedList<>();
        boolean[] secondChance = new boolean[cacheSize*cacheSize];

        int pageFaults = 0;
        for (int page : pages) {
            if (!cache.contains(page)) {
                pageFaults++;
                if (cache.size() == cacheSize) {
                    boolean pageReplaced = false;
                    while (!pageReplaced) {
                        int candidate = cache.poll();
                        if (secondChance[candidate]) {
                            secondChance[candidate] = false;
                            cache.offer(candidate);
                        } else {
                            pageReplaced = true;
                        }
                    }
                }
                cache.offer(page);
                secondChance[page] = true;
            } else {
                int index = getIndexInQueue(cache, page);
                if (index != -1) {
                    secondChance[page] = true;
                    // cache.remove(page);
                    // cache.offer(page);
                }
            }
        }

        return pageFaults;
    }

    private static int getIndexInQueue(Queue<Integer> queue, int page) {
        int index = 0;
        for (int element : queue) {
            if (element == page) {
                return index;
            }
            index++;
        }
        return -1;
    }


//OPT
    public static int optPageReplacement(int[] pages, int cacheSize) {
        Map<Integer, Integer> cache = new HashMap<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            if (!cache.containsKey(pages[i])) {
                pageFaults++;

                if (cache.size() < cacheSize) {
                    cache.put(pages[i], i);
                } else {
                    int pageToRemove = getPageToRemove(pages, cache, i);
                    cache.remove(pageToRemove);
                    cache.put(pages[i], i);
                }
            }
        }

        return pageFaults;
    }

    private static int getPageToRemove(int[] pages, Map<Integer, Integer> cache, int currentIndex) {
        int farthestIndex = -1;
        int pageToRemove = -1;

        for (Map.Entry<Integer, Integer> entry : cache.entrySet()) {
            int page = entry.getKey();
            int index = entry.getValue();

            if (!isPageInFuture(pages, currentIndex, page)) {
                return page;
            }

            if (index > farthestIndex) {
                farthestIndex = index;
                pageToRemove = page;
            }
        }

        return pageToRemove;
    }

    private static boolean isPageInFuture(int[] pages, int currentIndex, int page) {
        for (int i = currentIndex + 1; i < pages.length; i++) {
            if (pages[i] == page) {
                return true;
            }
        }
        return false;
    }

    //Random

    public static int randomPageReplacement(int[] pages, int cacheSize) {
        Set<Integer> cache = new HashSet<>();
        int pageFaults = 0;
        Random random = new Random();

        for (int page : pages) {
            if (!cache.contains(page)) {
                pageFaults++;
                if (cache.size() == cacheSize) {
                    // Usuń losową stronę z cache
                    int randomPageIndex = random.nextInt(cacheSize);
                    Integer[] cacheArray = cache.toArray(new Integer[0]);
                    cache.remove(cacheArray[randomPageIndex]);
                }
                cache.add(page);
            }
        }

        return pageFaults;
    }

}






    

