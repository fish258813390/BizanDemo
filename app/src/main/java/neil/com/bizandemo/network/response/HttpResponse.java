package neil.com.bizandemo.network.response;

/**
 * 描述:统一处理HttpResponse
 *
 * @author neil
 * @date 2018/3/13
 */
public class HttpResponse<T> {

    public T data; // 数据
    public T result;  // 数据
    public String message; // 信息
    public int code; // 服务器状态
    public T rank; // 数据 只有全区排行 json 有这个 字段

}
