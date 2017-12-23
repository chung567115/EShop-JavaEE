
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class TestPageHelper {

	@SuppressWarnings("resource")
	@Test
	public void testPageHelper() {
		// 创建一个Spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");

		// 从Spring容器中获得Mapper代理对象
		TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);

		// 分页
		PageHelper.startPage(2, 10);
		// 执行查询
		TbItemExample tbItemExample = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);

		// 取商品列表
		for (TbItem tbItem : list) {
			System.out.println(tbItem.getTitle());
		}

		// 取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		System.out.println("共有商品：" + total);

	}
}
