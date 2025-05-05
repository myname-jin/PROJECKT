/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestLogin;

/**
 *
 * @author adsd3
 */

// 최대 접속자 수를 관리하고 대기열을 처리하는 세션 관리자

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;


public class SessionManager {
    private final int maxUsers;
    private final Set<String> active = new HashSet<>();
    private final Queue<Pending> queue = new ArrayDeque<>();

    public SessionManager(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    /** 로그인 시도 결과 */
    public enum LoginDecision { OK, WAIT, FAIL_DUP }

    /** 대기 중인 클라이언트 정보 */
    public static class Pending {
        public final Socket socket;
        public final String userId;
        public final BufferedWriter out;
        public Pending(Socket socket, String userId, BufferedWriter out) {
            this.socket = socket;
            this.userId = userId;
            this.out    = out;
        }
    }

    /** 로그인 시도 */
    public synchronized LoginDecision tryLogin(String userId, Pending p) throws IOException {
        if (active.contains(userId)) {
            return LoginDecision.FAIL_DUP;
        }
        if (active.size() >= maxUsers) {
            queue.offer(p);
            return LoginDecision.WAIT;
        }
        active.add(userId);
        return LoginDecision.OK;
    }

    /** 로그아웃 처리 후, 대기열에 있는 클라이언트 하나를 깨웁니다 */
    public synchronized void logout(String userId) throws IOException {
        if (active.remove(userId) && !queue.isEmpty()) {
            Pending next = queue.poll();
            active.add(next.userId);
            next.out.write("OK:로그인 성공\n\n");
            next.out.flush();
        }
    }
}